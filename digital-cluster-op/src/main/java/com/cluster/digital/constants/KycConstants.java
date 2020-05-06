package com.cluster.digital.constants;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-06
 */
public interface KycConstants {
    enum KycType {
        AADHAR(1),
        VOTER_ID(2),
        DRIVING_LICENSE(3),
        PAN_CARD(4);
        private int kycType;

        KycType(int kycType) {
            this.kycType = kycType;
        }

        public int getUrl() {
            return kycType;
        }
    }
}
