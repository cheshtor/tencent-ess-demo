DROP TABLE IF EXISTS `corp_ess_company`;
CREATE TABLE `corp_ess_company` (
  `company_id` bigint(20) NOT NULL COMMENT '子客企业 ID',
  `admin_id` bigint(20) DEFAULT NULL COMMENT '子客管理员 ID',
  `company_name` varchar(128) DEFAULT NULL COMMENT '子客企业名称',
  `ess_company_name` varchar(128) DEFAULT NULL COMMENT '腾讯电子签返回的企业名称',
  `ess_social_credit_number` varchar(32) DEFAULT NULL COMMENT '腾讯电子签返回的企业统一社会信用代码',
  `ess_legal_name` varchar(20) DEFAULT NULL COMMENT '腾讯电子签返回的企业法人名称',
  `ess_id` varchar(128) DEFAULT NULL COMMENT '子客在腾讯电子签的 ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电子合同审计-企业表';


DROP TABLE IF EXISTS `corp_ess_company_certify_log`;
CREATE TABLE `corp_ess_company_certify_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `company_id` bigint(20) DEFAULT NULL COMMENT '子客企业 ID',
  `certify_status` varchar(16) DEFAULT NULL COMMENT '认证状态',
  `certify_comment` varchar(128) DEFAULT NULL COMMENT '认证状态备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电子合同审计-企业认证流程表';


DROP TABLE IF EXISTS `corp_ess_contract`;
CREATE TABLE `corp_ess_contract` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `contract_id` varchar(128) DEFAULT NULL COMMENT '业务系统合同 ID',
  `flow_id` varchar(128) DEFAULT NULL COMMENT '腾讯电子签合同 ID',
  `release_id` varchar(128) DEFAULT NULL COMMENT '此合同对应的解除协议的 ID',
  `flow_type` tinyint(2) DEFAULT '1' COMMENT '腾讯电子签合同类型。1 - 正常合同， 2 - 解除协议',
  `company_id` bigint(20) DEFAULT NULL COMMENT '合同发起子客 ID',
  `contract_name` varchar(128) DEFAULT NULL COMMENT '合同名称',
  `contract_type` varchar(128) DEFAULT NULL COMMENT '合同类型',
  `template_id` varchar(128) DEFAULT NULL COMMENT '合同使用模板的 ID',
  `contract_deadline` bigint(20) DEFAULT NULL COMMENT '合同签署截止时间',
  `first_party_name` varchar(32) DEFAULT NULL COMMENT '子客企业名称',
  `first_party_mobile` varchar(20) DEFAULT NULL COMMENT '子客企业管理员手机号',
  `first_party_idcard` varchar(20) DEFAULT NULL COMMENT '子客企业管理员身份证号',
  `second_party_name` varchar(32) DEFAULT NULL COMMENT '乙方名称',
  `second_party_mobile` varchar(20) DEFAULT NULL COMMENT '乙方手机号',
  `second_party_idcard` varchar(20) DEFAULT NULL COMMENT '乙方身份证号',
  `sign_status` varchar(20) DEFAULT 'INIT' COMMENT '合同签署状态',
  `sign_status_message` varchar(64) DEFAULT NULL COMMENT '合同签署状态描述',
  `first_party_sign_status` varchar(20) DEFAULT 'PENDING' COMMENT '甲方签署状态',
  `first_party_sign_status_message` varchar(64) DEFAULT NULL COMMENT '甲方签署状态描述',
  `first_party_sign_time` bigint(20) DEFAULT NULL COMMENT '甲方签署时间',
  `second_party_sign_status` varchar(20) DEFAULT 'PENDING' COMMENT '乙方签署状态',
  `second_party_sign_status_message` varchar(64) DEFAULT NULL COMMENT '乙方签署状态描述',
  `second_party_sign_time` bigint(20) DEFAULT NULL COMMENT '乙方签署时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电子合同审计-合同表';


DROP TABLE IF EXISTS `corp_ess_contract_log`;
CREATE TABLE `corp_ess_contract_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `flow_id` varchar(128) DEFAULT NULL COMMENT '电子签合同 ID',
  `sign_status` varchar(20) DEFAULT 'INIT' COMMENT '合同签署状态',
  `sign_status_message` varchar(64) DEFAULT NULL COMMENT '合同签署状态描述',
  `first_party_sign_status` varchar(20) DEFAULT 'PENDING' COMMENT '甲方签署状态',
  `first_party_sign_status_message` varchar(64) DEFAULT NULL COMMENT '甲方签署状态描述',
  `first_party_sign_time` bigint(20) DEFAULT NULL COMMENT '甲方签署时间',
  `second_party_sign_status` varchar(20) DEFAULT 'PENDING' COMMENT '乙方签署状态',
  `second_party_sign_status_message` varchar(64) DEFAULT NULL COMMENT '乙方签署状态描述',
  `second_party_sign_time` bigint(20) DEFAULT NULL COMMENT '乙方签署时间',
  `callback_time` bigint(20) DEFAULT NULL COMMENT '回调发起时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电子合同审计-合同日志表';