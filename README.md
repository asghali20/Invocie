

-   Using Open API schema under /resources/specs
-   Dynamicly generating the model clasess from the schema in the pom.xml
-   Adding Docker file for image deployment.
-   Adding Jenkinsfile for pipline CI/CD deployments and sonarqube configurations
-   data/ request validation using openApi schema via RestControllerAdvice


-   Java 17+
-   Spring Boot 3.x
-   Spring Web + Spring Validation
-   Use Strategy Pattern for tax rules per country
-   Use Service and Repository layers with in-memory storage (Map)
-   Use DTOs + ModelMapper or MapStruct
-   Handle currency formatting using Java’s Locale and `Currency` APIs
-   Implement proper exception handling using `@ControllerAdvice`  


List of APIs with endpoint and sample request/response in sequasial order:

    addClient
    
    localhost:8080/sag-invoice/v1/addClient

    request:
    {
     "id" : "01",
     "name" : "salah",
     "countryCode" : "DE",
     "preferredCurrency": "USD"

   }

    getClient
    localhost:8080/sag-invoice/v1/getClient?id=01


    addInvoice
    
    localhost:8080/sag-invoice/v1/addInvoice

    sample request 1:
    {
        "id" : "inv08",
        "clientId" : "01",
        "issueDate" : "2019-05-17",
         "lineItems": [
              {
                  "quantity": "5",
                  "unitPrice": "10",
                  "description" : "cable"
              } 
         ]

      }

      sample request 2:
        {
          "id" : "inv09",
          "clientId" : "01",
          "issueDate" : "2019-05-17",
           "lineItems": [
                {
                    "quantity": "10",
                    "unitPrice": "5",
                    "description" : "mouse"
                } 
           ]

        }



    finalizeInvoice
    localhost:8080/sag-invoice/v1/finalizeInvoice?id=inv09
    response:
       {
        "id": "inv09",
        "clientId": "01",
        "issueDate": "2019-05-17",
        "status": "FINALIZED",
        "lineItems": [
            {
                "description": "mouse",
                "quantity": 10,
                "unitPrice": 5
            }
        ],
        "tax": null,
        "subTotal": null,
        "total": 50.0
    }
    

    paidInvoice
    localhost:8080/sag-invoice/v1/paidInvoice?id=inv09
    sample response:
    {
        "id": "inv09",
        "clientId": "01",
        "issueDate": "2019-05-17",
        "status": "PAID",
        "lineItems": [
            {
                "description": "mouse",
                "quantity": 10,
                "unitPrice": 5
            }
        ],
        "tax": null,
        "subTotal": null,
        "total": 50.0
    }




    getInvoice
    localhost:8080/sag-invoice/v1/getInvoice?id=inv08
    sample response:
    {
        "id": "inv08",
        "clientId": "01",
        "issueDate": "2019-05-17",
        "status": null,
        "lineItems": [
            {
                "description": "cable",
                "quantity": 5,
                "unitPrice": 10
            }
        ],
        "tax": 15.0,
        "subTotal": null,
        "total": null
    }


    generateMonthlyReport
    localhost:8080/sag-invoice/v1/generateMonthlyReport

    sample response:
    "Total Invoices","Total Amount","Total Taxes Paid","Average Invoice ","Count of unpaid Invocies"
    "2","50.0","15.0","25.0","1"




Included:
      sag-invocie-v1-sequance.jpg
      Arch-Document

Susgestion:
     for tax rules alternate solution is using drools framwork to provide taxing for each country.
      