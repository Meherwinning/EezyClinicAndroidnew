package com.vempower.eezyclinic.core;

import java.io.File;

public class SecondaryInsurance {
    public final String id;
    public String insurancePackage;
    public String insuranceNumber;
    public String tpa;
    public String tpaid;
    public String policy;
    public String policy_number;
    public String memberid;
    public String type;
    public String scheme;
    public String reason;
    public String copay;
    public String maxlimit;
    public String fromvalidity;
    public String tovalidity;

    public File frontImageFile,backImageFile;
   // public String insurance_card_front;
   // public String insurance_card_rear;
   public SecondaryInsurance() {
       this.id = null;
   }

    public SecondaryInsurance(String id) {
        this.id = id;
    }





    /*
    {
          "access_key" : "4951dbdf961f4156a02d7394bc5fa3e8",

                  "insurancePackage": "Bharti Axa",
                  "insuranceNumber": "ins12356",
                   "tpa": "1",
          "tpaid": "taa1235",
         "policy": "polcyli12",
        "policy_number": "1657578575",
         "memberid": "546565",
      "type": "multi",
       "scheme": "etewte",
       "reason": "etewty",
         "copay": "25",
       "maxlimit": "50",
        "fromvalidity": "2018-01-09",
                "tovalidity": "2018-05-09",

       "insurance_card_front": "http://202.63.103.194:8003/uploads/1517295719_59452.jpg",
        "insurance_card_rear": "http://202.63.103.194:8003/uploads/1517295719_42683.jpg"

          }
     */
}
