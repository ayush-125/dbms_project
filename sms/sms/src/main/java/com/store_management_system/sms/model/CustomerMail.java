package com.store_management_system.sms.model;


public class CustomerMail {
        private Long customerId;
        private String customerEmail;


        
        public boolean isEmpty(){
            return(customerEmail==null || customerEmail.isEmpty()) && customerId==null;
        }

        public Long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Long customerId) {
            this.customerId = customerId;
        }

        public String getCustomerEmail() {
            return customerEmail;
        }

        public void setCustomerEmail(String customerEmail) {
            this.customerEmail = customerEmail;
        }
        
}
