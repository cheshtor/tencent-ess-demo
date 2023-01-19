CREATE TABLE `corp_ess_company`
(
    `company_id`   BIGINT(20) COMMENT '子客企业 ID',
    `admin_id`     BIGINT(20) COMMENT '子客管理员 ID',
    `company_name` VARCHAR(128) COMMENT '子客企业名称',
    `ess_id`       VARCHAR(128) COMMENT '子客在腾讯电子签的 ID',
    `create_time`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_time`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    PRIMARY KEY `pk_company_id` (`company_id`)
);

CREATE TABLE `corp_ess_contract`
(
    `id`                       BIGINT(20) COMMENT '主键ID',
    `contract_id`              VARCHAR(128) COMMENT '业务系统合同 ID',
    `flow_id`                  VARCHAR(128) COMMENT '腾讯电子签合同 ID',
    `release_id`               VARCHAR(128) COMMENT '此合同对应的解除协议的 ID',
    `flow_type`                TINYINT(2)        DEFAULT 1 COMMENT '腾讯电子签合同类型。1 - 正常合同， 2 - 解除协议',
    `company_id`               BIGINT(20) COMMENT '合同发起子客 ID',
    `contract_name`            VARCHAR(128) COMMENT '合同名称',
    `contract_type`            VARCHAR(128) COMMENT '合同类型',
    `template_id`              VARCHAR(128) COMMENT '合同使用模板的 ID',
    `contract_deadline`        BIGINT(20) COMMENT '合同签署截止时间',
    `first_party_name`         VARCHAR(32) COMMENT '子客企业名称',
    `first_party_mobile`       VARCHAR(20) COMMENT '子客企业管理员手机号',
    `first_party_idcard`       VARCHAR(20) COMMENT '子客企业管理员身份证号',
    `second_party_name`        VARCHAR(32) COMMENT '乙方名称',
    `second_party_mobile`      VARCHAR(20) COMMENT '乙方手机号',
    `second_party_idcard`      VARCHAR(20) COMMENT '乙方身份证号',
    `sign_status`              VARCHAR(20)       DEFAULT 'INIT' COMMENT '合同签署状态',
    `first_party_sign_status`  VARCHAR(20)       DEFAULT 'PENDING' COMMENT '甲方签署状态',
    `first_party_sign_time`    BIGINT(20) COMMENT '甲方签署时间',
    `second_party_sign_status` VARCHAR(20)       DEFAULT 'PENDING' COMMENT '乙方签署状态',
    `second_party_sign_time`   BIGINT(20) COMMENT '乙方签署时间',
    `create_time`              DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_time`              DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    PRIMARY KEY `pk_id` (`id`)
);

CREATE TABLE `corp_ess_contract_log`
(
    `id`                       BIGINT(20) AUTO_INCREMENT COMMENT '主键 ID',
    `flow_id`                  VARCHAR(128) COMMENT '电子签合同 ID',
    `sign_status`              VARCHAR(20)       DEFAULT 'INIT' COMMENT '合同签署状态',
    `first_party_sign_status`  VARCHAR(20)       DEFAULT 'PENDING' COMMENT '甲方签署状态',
    `first_party_sign_time`    BIGINT(20) COMMENT '甲方签署时间',
    `second_party_sign_status` VARCHAR(20)       DEFAULT 'PENDING' COMMENT '乙方签署状态',
    `second_party_sign_time`   BIGINT(20) COMMENT '乙方签署时间',
    `create_time`              DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY `pk_id` (`id`)
);