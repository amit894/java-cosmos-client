package com.demo;

import com.azure.cosmos.*;
//import com.azure.cosmos.models.CosmosDatabaseResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import com.azure.core.credential.TokenCredential;
//import com.azure.identity.ClientSecretCredentialBuilder;
//import com.azure.identity.DefaultAzureCredentialBuilder;
//import java.util.List;


import java.time.Duration;



public class CosmosClientDemo {



    public void execute1() {

        executeWorkflow1();

    }


    private static void executeWorkflow1() {

        int maxPoolSize =100 ;
        int maxRetryAttempts = 10;
        int retryWaitTimeInSeconds = 1;
//

        String hostName = "https://medium-blog.documents.azure.com:443/";
        String masterKey= "secret";
        //String databaseName= "AzureSampleFamilyDatabase";
        ConsistencyLevel consistencyLevel = ConsistencyLevel.SESSION;
        CosmosClient syncClient = null;
        //CosmosDatabase syncDatabase = null;


        System.out.println("Payload size : " + 500);
        syncClient = buildCosmosClient(ConnectionMode.DIRECT, maxPoolSize, maxRetryAttempts, retryWaitTimeInSeconds, hostName, masterKey, consistencyLevel);
        //syncDatabase = getDB(syncClient, databaseName);
        System.out.println(syncClient);

    }


//    private static CosmosDatabase getDB(CosmosClient client, String database) {
//        CosmosDatabaseResponse databaseResponse = null;
//        try {
//            databaseResponse = client.createDatabaseIfNotExists(database);
//        } catch(CosmosException e) {
//            e.printStackTrace();
//        }
//
//        return client.getDatabase(database);
//    }

    private static ThrottlingRetryOptions getRetryOptions(int maxRetryAttempts,
                                                          int retryWaitTimeInSeconds) {
        ThrottlingRetryOptions retryOptions = new ThrottlingRetryOptions();
        retryOptions.setMaxRetryAttemptsOnThrottledRequests(maxRetryAttempts);
        retryOptions.setMaxRetryWaitTime(Duration.ofSeconds(retryWaitTimeInSeconds));

        return retryOptions;
    }


    private static CosmosClient buildCosmosClient(ConnectionMode connectionMode, int maxPoolSize, int maxRetryAttempts,
                                                  int retryWaitTimeInSeconds, String hostName, String masterKey, ConsistencyLevel consistencyLevel ) {

        ThrottlingRetryOptions retryOptions = getRetryOptions(maxRetryAttempts, retryWaitTimeInSeconds);

        return new CosmosClientBuilder()
                .endpoint(hostName)
                .key(masterKey)
                .directMode(DirectConnectionConfig.getDefaultConfig().setIdleConnectionTimeout(Duration.ofMinutes(15)))
                .throttlingRetryOptions(retryOptions)
                .consistencyLevel( consistencyLevel )
                .buildClient();

    }

//    private static CosmosClient buildCosmosClientToken(ConnectionMode connectionMode, int maxPoolSize, int maxRetryAttempts,
//        int retryWaitTimeInSeconds, String hostName, String masterKey, ConsistencyLevel consistencyLevel ) {
//
//            ThrottlingRetryOptions retryOptions = getRetryOptions(maxRetryAttempts, retryWaitTimeInSeconds);
//            TokenCredential tokenCredential = new DefaultAzureCredentialBuilder().build();
//
//        TokenCredential ServicePrincipal = new ClientSecretCredentialBuilder()
//                .authorityHost("https://login.microsoftonline.com")
//                .tenantId("x")
//                .clientId("y")
//                .clientSecret("z")
//                .build();
//
//        return new CosmosClientBuilder()
//                    .endpoint(hostName)
//                    .key(ServicePrincipal)
//                    .directMode(DirectConnectionConfig.getDefaultConfig().setIdleConnectionTimeout(Duration.ofMinutes(15)))
//                    .throttlingRetryOptions(retryOptions)
//                    .consistencyLevel( consistencyLevel )
//                    .buildClient();    private static CosmosClient buildCosmosClientToken(ConnectionMode connectionMode, int maxPoolSize, int maxRetryAttempts,
////        int retryWaitTimeInSeconds, String hostName, String masterKey, ConsistencyLevel consistencyLevel ) {
////
////            ThrottlingRetryOptions retryOptions = getRetryOptions(maxRetryAttempts, retryWaitTimeInSeconds);
////            TokenCredential tokenCredential = new DefaultAzureCredentialBuilder().build();
////
////        TokenCredential ServicePrincipal = new ClientSecretCredentialBuilder()
////                .authorityHost("https://login.microsoftonline.com")
////                .tenantId("x")
////                .clientId("y")
////                .clientSecret("z")
////                .build();
////
////        return new CosmosClientBuilder()
////                    .endpoint(hostName)
////                    .key(ServicePrincipal)
////                    .directMode(DirectConnectionConfig.getDefaultConfig().setIdleConnectionTimeout(Duration.ofMinutes(15)))
////                    .throttlingRetryOptions(retryOptions)
////                    .consistencyLevel( consistencyLevel )
////                    .buildClient();
////
////        }
//
//        }

    public static void main(String[] args)
    {
        System.out.println("I am a Geek");
        CosmosClientDemo C1 = new CosmosClientDemo();
        C1.execute1();
    }

}

