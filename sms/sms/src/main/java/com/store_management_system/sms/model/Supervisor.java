package com.store_management_system.sms.model;

public class Supervisor {
    private Long worker;
    private Long supervisor;

    public boolean isEmpty(){
        return (worker==null && supervisor==null);
    }
    public Long getSupervisor() {
        return supervisor;
    }
    public void setSupervisor(Long supervisor) {
        this.supervisor = supervisor;
    }
    public Long getWorker() {
        return worker;
    }
    public void setWorker(Long worker) {
        this.worker = worker;
    }
}
