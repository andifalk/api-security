package com.example.vehicle.api;

import java.net.URI;

public class WorkshopNotification {
    private String problem;
    private URI partnerApi;

    public WorkshopNotification() {
    }

    public WorkshopNotification(String problem, URI partnerApi) {
        this.problem = problem;
        this.partnerApi = partnerApi;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public URI getPartnerApi() {
        return partnerApi;
    }

    public void setPartnerApi(URI partnerApi) {
        this.partnerApi = partnerApi;
    }

    @Override
    public String toString() {
        return "WorkshopNotification{" +
                "problem='" + problem + '\'' +
                ", partnerApi=" + partnerApi +
                '}';
    }
}
