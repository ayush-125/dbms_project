package com.store_management_system.sms.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.model.*;

@Repository
public class DiscountRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ProductDiscountRepository productDiscountRepository;

    public List<Discount> findAll(){
        try {
            String sql="select * from discount";
            List<Discount> discounts=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Discount.class));
            
            for(Discount discount:discounts){
                discount.setProductIds(productDiscountRepository.findByDiscountId(discount.getId()));
            }
            return discounts;
        } catch (DataAccessException e) {
            System.err.println("Error querying Discount  " + e.getMessage());
            throw new CustomDatabaseException("Error querying discount "+e.getMessage(),e);
        
        }
    }


    public Discount findById(Long id){
        try {
            String sql="select * from discount where id=?";
            List<Discount> discounts=jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Discount.class),id);
            if(discounts.isEmpty()){
                return null;
            }
            Discount discount=discounts.get(0);
            discount.setProductIds(productDiscountRepository.findByDiscountId(discount.getId()));
            return discount;
        } catch (DataAccessException e) {
            System.err.println("Error querying Discount  " + e.getMessage());
            throw new CustomDatabaseException("Error querying discount "+e.getMessage(),e);
        
        }
    }

    public void save(Discount discount){
        try {
            if(discount.getId()==null){
                String sql="insert into discount(description,dos,doe,rate) values(?,?,?,?)";
                jdbcTemplate.update(sql, discount.getDescription(),discount.getDos(),discount.getDoe(),discount.getRate());

                String sqll="select id from discount where description=? and dos=? and doe=? and rate=?";
                List<Long> discountIds=jdbcTemplate.queryForList(sqll,Long.class,discount.getDescription(),discount.getDos(),discount.getDoe(),discount.getRate());
                Long discountId=discountIds.get(0);
                for(Long productId:discount.getProductIds()){
                    productDiscountRepository.save(discountId,productId);
                }
            }else{
                String sql="update discount set description=?,dos=?,doe=?,rate=? where id=?";
                jdbcTemplate.update(sql,discount.getDescription(),discount.getDos(),discount.getDoe(),discount.getRate(),discount.getId());
                productDiscountRepository.deleteByDiscountId(discount.getId());
                for(Long productId:discount.getProductIds()){
                    productDiscountRepository.save(discount.getId(),productId);
                }
            
            }
        } catch (DataAccessException e) {
            System.err.println("Error saving or updating discount  "+ e.getMessage());
            throw new CustomDatabaseException("Error saving or updating discount  "+e.getMessage(),e);
        
        }
    }

    public void delete(Long id){
        try {
            String sql="delete from discount where id=?";
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting discount  " + e.getMessage());
            throw new CustomDatabaseException("Error deleting discount"+e.getMessage(),e);
       
        }
    }
}
