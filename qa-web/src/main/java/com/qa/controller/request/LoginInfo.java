
package com.qa.controller.request;

public class LoginInfo {

    /** 企业标识 */
    private String corpId;

    /** 凭证密钥 */
    private String corpSecret;

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpSecret() {
        return corpSecret;
    }

    public void setCorpSecret(String corpSecret) {
        this.corpSecret = corpSecret;
    }

}
