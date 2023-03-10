INSERT INTO `enterprise_data` (`data_name`, `data_value`, `data_annotation`) VALUES ('agentId', '', '自建应用id(此参数为身份唯一标识，请保密)');
INSERT INTO `enterprise_data` (`data_name`, `data_value`, `data_annotation`) VALUES ('apiKey', '', '天行数据api密钥(此参数为密钥，请保密)');
INSERT INTO `enterprise_data` (`data_name`, `data_value`, `data_annotation`) VALUES ('corpId', '', '企业id(此参数为身份唯一标识，请保密)');
INSERT INTO `enterprise_data` (`data_name`, `data_value`, `data_annotation`) VALUES ('dateEnding', '2023-07-31', '放假日期');
INSERT INTO `enterprise_data` (`data_name`, `data_value`, `data_annotation`) VALUES ('dateStarting', '2023-02-20', '开学日期');
INSERT INTO `enterprise_data` (`data_name`, `data_value`, `data_annotation`) VALUES ('debugPeriod', '', '调试周期(如不启用调试，请置空)');
INSERT INTO `enterprise_data` (`data_name`, `data_value`, `data_annotation`) VALUES ('debugWeek', '', '调试星期(如不启用调试，请置空)');
INSERT INTO `enterprise_data` (`data_name`, `data_value`, `data_annotation`) VALUES ('departmentId', '1', '推送部门ID(此参数用于控制被推送的部门成员)');
INSERT INTO `enterprise_data` (`data_name`, `data_value`, `data_annotation`) VALUES ('imgUrl', 'http://prefersmin.xyz/images/image5.jpg', '图文消息的图片url');
INSERT INTO `enterprise_data` (`data_name`, `data_value`, `data_annotation`) VALUES ('morningClassDays', '0', '上过的早课天数(此项不可为空)');
INSERT INTO `enterprise_data` (`data_name`, `data_value`, `data_annotation`) VALUES ('nightClassDays', '0', '上过的晚课天数(此项不可为空)');
INSERT INTO `enterprise_data` (`data_name`, `data_value`, `data_annotation`) VALUES ('pushTime', '1', '推送时间(为 0 则每日早晨推送当日课程与天气，为 1 则每日夜间推送明日课程与天气)');
INSERT INTO `enterprise_data` (`data_name`, `data_value`, `data_annotation`) VALUES ('secret', '', '自建应用secret(此参数为密钥，请保密)');
INSERT INTO `enterprise_data` (`data_name`, `data_value`, `data_annotation`) VALUES ('totalClassTimes', '0', '累计上过的课程次数(此项不可为空)');
INSERT INTO `enterprise_data` (`data_name`, `data_value`, `data_annotation`) VALUES ('totalSpecializedClassTimes', '0', '累计上过的专业课程次数(此项不可为空)');
INSERT INTO `enterprise_data` (`data_name`, `data_value`, `data_annotation`) VALUES ('url', 'http://prefersmin.xyz/', '卡片消息的跳转url');
INSERT INTO `enterprise_data` (`data_name`, `data_value`, `data_annotation`) VALUES ('weatherValue', '', '天气推送位置(详见天行数据天气预报api文档)');
