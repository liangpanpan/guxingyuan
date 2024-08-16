package com.guxingyuan.logback;

public class DigitalRisk {
    /**
     * 风险Id
     */
    private String riskID;

    /**
     * 风险类型Id
     */
    private int serviceType;

    /**
     * 网址
     */
    private String url;

    /**
     * 标记
     */
    private String[] tag;

    /**
     * 发现时间
     */
    private String detectTime;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 解析地址
     */
    private String ip;

    /**
     * 地理位置
     */
    private String location;

    /**
     * asn
     */
    private String[] asn;

    /**
     * 运营商
     */
    private String[] carrier;

    /**
     * 侵权项，截图图片下载url地址
     */
    private String[] abuseSubject;

    /**
     * 举证截图，截图图片下载url地址
     */
    private String[] evidence;

    /**
     * 备注
     */
    private String note;

    /**
     * 平台名称
     */
    private String platform;

    /**
     * 欺诈账号
     */
    private String fraudAccount;

    /**
     * 账号ID
     */
    private String accountID;

    /**
     * 账号描述
     */
    private String description;

    /**
     * 欺诈内容或内容
     */
    private String artContent;

    /**
     * 标题
     */
    private String title;

    /**
     * 上传人
     */
    private String uploader;

    /**
     * 泄露内容
     */
    private String leakage;

    /**
     * 风险版本号
     */
    private String riskVersion;

    /**
     * 风险等级
     */
    private String riskLevel;

    /**
     * 最新发现时间
     */
    private String lastDetectTime;

    /**
     * 更新时间
     */
    private String updatedTime;

    /**
     * 风险状态  2.潜在风险 3.已忽略风险
     * 0: 潜在风险
     * 1: 已忽略风险
     */
    private Long status;

    /**
     * 误报厂商
     */
    private String falseReportCompany;

    /**
     * 引擎版本
     */
    private String engineVersion;

    /**
     * App路径
     */
    private String appPath;

    /**
     * Sha256
     */
    private String appSha256;

    /**
     * 误报浏览器名
     */
    private String assetName;

    /**
     * 其他误报源
     */
    private String falseReportUrl;

    /**
     * 补充材料，补充材料下载url地址
     */
    private String[] supplementalMaterial;

    /**
     * 社交媒体
     */
    private String socialAccount;

    /**
     * 盗版图片
     */
    private String[] leakageContent;

    /**
     * 发布时间，侵权欺诈（内容）
     */
    private String pubtime;

    /**
     * 命中关键字
     */
    private String hitKeywords;

    /**
     * 命中位置
     */
    private String hitPosition;

    /**
     * 评论数
     */
    private String commentNum;

    /**
     * 点赞数
     */
    private String likeNum;

    /**
     * 阅读量
     */
    private String readNum;

    /**
     * 误报应用
     */
    private String appName;

    /**
     * 引擎名称
     */
    private String engineName;

    /**
     * 检测结果
     */
    private String detectResult;

    /**
     * TG群组名称
     */
    private String telegramGroup;


    public String getRiskID() {
        return riskID;
    }

    public void setRiskID(String riskID) {
        this.riskID = riskID;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getTag() {
        return tag;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
    }

    public String getDetectTime() {
        return detectTime;
    }

    public void setDetectTime(String detectTime) {
        this.detectTime = detectTime;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String[] getAsn() {
        return asn;
    }

    public void setAsn(String[] asn) {
        this.asn = asn;
    }

    public String[] getCarrier() {
        return carrier;
    }

    public void setCarrier(String[] carrier) {
        this.carrier = carrier;
    }

    public String[] getEvidence() {
        return evidence;
    }

    public void setEvidence(String[] evidence) {
        this.evidence = evidence;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getFraudAccount() {
        return fraudAccount;
    }

    public void setFraudAccount(String fraudAccount) {
        this.fraudAccount = fraudAccount;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getAbuseSubject() {
        return abuseSubject;
    }

    public void setAbuseSubject(String[] abuseSubject) {
        this.abuseSubject = abuseSubject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getLeakage() {
        return leakage;
    }

    public void setLeakage(String leakage) {
        this.leakage = leakage;
    }

    public String getRiskVersion() {
        return riskVersion;
    }

    public void setRiskVersion(String riskVersion) {
        this.riskVersion = riskVersion;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getFalseReportCompany() {
        return falseReportCompany;
    }

    public void setFalseReportCompany(String falseReportCompany) {
        this.falseReportCompany = falseReportCompany;
    }

    public String getEngineVersion() {
        return engineVersion;
    }

    public void setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion;
    }

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    public String getAppSha256() {
        return appSha256;
    }

    public void setAppSha256(String appSha256) {
        this.appSha256 = appSha256;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getSocialAccount() {
        return socialAccount;
    }

    public void setSocialAccount(String socialAccount) {
        this.socialAccount = socialAccount;
    }

    public String[] getLeakageContent() {
        return leakageContent;
    }

    public void setLeakageContent(String[] leakageContent) {
        this.leakageContent = leakageContent;
    }

    public String getArtContent() {
        return artContent;
    }

    public void setArtContent(String artContent) {
        this.artContent = artContent;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getLastDetectTime() {
        return lastDetectTime;
    }

    public void setLastDetectTime(String lastDetectTime) {
        this.lastDetectTime = lastDetectTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getFalseReportUrl() {
        return falseReportUrl;
    }

    public void setFalseReportUrl(String falseReportUrl) {
        this.falseReportUrl = falseReportUrl;
    }

    public String[] getSupplementalMaterial() {
        return supplementalMaterial;
    }

    public void setSupplementalMaterial(String[] supplementalMaterial) {
        this.supplementalMaterial = supplementalMaterial;
    }

    public String getPubtime() {
        return pubtime;
    }

    public void setPubtime(String pubtime) {
        this.pubtime = pubtime;
    }

    public String getHitKeywords() {
        return hitKeywords;
    }

    public void setHitKeywords(String hitKeywords) {
        this.hitKeywords = hitKeywords;
    }

    public String getHitPosition() {
        return hitPosition;
    }

    public void setHitPosition(String hitPosition) {
        this.hitPosition = hitPosition;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public String getReadNum() {
        return readNum;
    }

    public void setReadNum(String readNum) {
        this.readNum = readNum;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public String getDetectResult() {
        return detectResult;
    }

    public void setDetectResult(String detectResult) {
        this.detectResult = detectResult;
    }

    public String getTelegramGroup() {
        return telegramGroup;
    }

    public void setTelegramGroup(String telegramGroup) {
        this.telegramGroup = telegramGroup;
    }
}
