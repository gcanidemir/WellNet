package com.personal.billing_service.grpc;

import org.slf4j.Logger;

import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest, StreamObserver<billing.BillingResponse> responseObserver) {

        log.info("createBillingAccount request recieved: {}", billingRequest);

        BillingResponse billingResponse = BillingResponse.newBuilder()
                .setAccountId("12345")
                .setStatus("Account created successfully")
                .build();

        responseObserver.onNext(billingResponse);
        responseObserver.onCompleted();
    }



}
