
/*商品SKU表*/
DROP TABLE IF EXISTS `product_sku_online`;
CREATE TABLE `product_sku_online`  (
  `id` varchar(128)  NOT NULL COMMENT '主键',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `creator` varchar(128)  NOT NULL COMMENT '创建人',
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `modifier` varchar(128)  NULL DEFAULT NULL COMMENT '修改人',
  `product_spu_online_id` varchar(128)  NOT NULL COMMENT 'spu_id',
  `sort_number` int(5) NULL DEFAULT 0 COMMENT '排序号',
  `price` int(10) NOT NULL COMMENT '网店售价',
  `currency` varchar(255)  NOT NULL COMMENT '币制',
  `quantity` varchar(255)  NOT NULL COMMENT '可售库存',
  `store_id` varchar(128)  NOT NULL COMMENT '店铺编号',
  `origin_price` int(255) NULL DEFAULT NULL COMMENT '商品划线价格，例如 888',
  `rate` decimal(10, 6) NULL DEFAULT NULL COMMENT '当前汇率',
  `images` text  NULL COMMENT '图片',
  `weight` decimal(10, 4) NULL DEFAULT NULL COMMENT '重量',
  `name` varchar(255)  NULL DEFAULT NULL COMMENT '名称',
  `sku_no` varchar(128)  NULL DEFAULT NULL COMMENT 'sku条码',
  `sku_code` varchar(128)  NULL DEFAULT NULL COMMENT 'sku编码',
  `item_type` varchar(128)  NULL DEFAULT NULL COMMENT '商品类目',
  `image` varchar(255)  NULL DEFAULT NULL COMMENT '规格图片',
  `cost_price` int(11) NULL DEFAULT NULL COMMENT '成本价',
  `sold_number` int(11) NULL DEFAULT 0 COMMENT '销售数量，默认为0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;



/*商品SPU表*/
DROP TABLE IF EXISTS `product_spu_online`;
CREATE TABLE `product_spu_online`  (
  `id` varchar(128)  NOT NULL COMMENT '主键',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `creator` varchar(128)  NOT NULL COMMENT '创建人',
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `modifier` varchar(128)  NULL DEFAULT NULL COMMENT '修改人',
  `name` varchar(255)  NOT NULL COMMENT '商品名称',
  `quantity` varchar(255)  NOT NULL COMMENT '可售库存',
  `origin_price` int(255) NULL DEFAULT NULL COMMENT '商品划线价格，例如 888',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '销售状态：0 已售罄|1 销售中 | 2 仓库中 | 3 未知',
  `description` text  NULL COMMENT '描述',
  `store_id` varchar(255)  NOT NULL COMMENT '店铺编号',
  `category_id` varchar(255)  NULL DEFAULT NULL COMMENT '商品分类',
  `currency` varchar(255)  NOT NULL COMMENT '币制',
  `post_fee` int(11) NULL DEFAULT 0,
  `sort_number` int(5) NOT NULL DEFAULT 0 COMMENT '排序号',
  `sold_number` int(5) NULL DEFAULT 0 COMMENT '总销量',
  `images` text  NULL COMMENT '图片地址',
  `price` int(10) NULL DEFAULT NULL COMMENT '网店售价',
  `sub_title` varchar(255)  NULL DEFAULT NULL COMMENT '副标题',
  `summary` text  NULL COMMENT '简介',
  `marketing_image` varchar(255)  NULL DEFAULT NULL COMMENT '产品封面',
  `has_sku` int(1) NULL DEFAULT NULL COMMENT '是否拥有SKU规格商品',
  `weight` decimal(10, 4) NULL DEFAULT NULL,
  `spu_code` varchar(255)  NULL DEFAULT NULL COMMENT 'SPU编号',
  `spu_no` varchar(255)  NULL DEFAULT NULL COMMENT 'SPU条码',
  `cost_price` int(11) NULL DEFAULT NULL COMMENT '成本价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;


/*顾客地址*/
DROP TABLE IF EXISTS `shop_customer_address`;
CREATE TABLE `shop_customer_address`  (
  `id` varchar(128)  NOT NULL COMMENT '主键',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `creator` varchar(128)  NOT NULL COMMENT '创建人',
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `modifier` varchar(128)  NULL DEFAULT NULL COMMENT '修改人',
  `name` varchar(255)  NULL DEFAULT NULL COMMENT '收件人姓名',
  `mobile` varchar(255)  NULL DEFAULT NULL COMMENT '手机号',
  `nation_code` varchar(255)  NULL DEFAULT NULL COMMENT '手机区域码',
  `address` varchar(255)  NULL DEFAULT NULL COMMENT '地址',
  `country` varchar(255)  NULL DEFAULT NULL COMMENT '国家',
  `province` varchar(255)  NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(255)  NULL DEFAULT NULL COMMENT '市',
  `postal_code` varchar(255)  NULL DEFAULT NULL COMMENT '邮政编码',
  `district` varchar(255)  NULL DEFAULT NULL COMMENT '区/县',
  `customer_id` varchar(255)  NULL DEFAULT NULL COMMENT '顾客编号',
  `lon` varchar(255)  NULL DEFAULT NULL COMMENT '经度',
  `lat` varchar(255)  NULL DEFAULT NULL COMMENT '纬度',
  `shop_id` varchar(255)  NULL DEFAULT NULL COMMENT '店铺编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;


/*订单*/
DROP TABLE IF EXISTS `shop_customer_order`;
CREATE TABLE `shop_customer_order`  (
  `id` varchar(128)  NOT NULL COMMENT '主键',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `creator` varchar(128)  NOT NULL COMMENT '创建人',
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `modifier` varchar(128)  NULL DEFAULT NULL COMMENT '修改人',
  `shop_id` varchar(128)  NOT NULL COMMENT '店铺编号',
  `order_no` varchar(128)  NOT NULL COMMENT '订单号',
  `customer_id` varchar(128)  NOT NULL COMMENT '顾客编号',
  `order_status` int(1) NOT NULL DEFAULT 0 COMMENT '订单状态',
  `order_source` int(1) NOT NULL DEFAULT 0 COMMENT '订单来源',
  `order_type` int(1) NOT NULL DEFAULT 0 COMMENT '订单类型',
  `remark` varchar(128)  NULL DEFAULT NULL COMMENT '买家备注信息',
  `consignee` varchar(128)  NULL DEFAULT NULL COMMENT '收货信息',
  `mobile` varchar(128)  NULL DEFAULT NULL COMMENT '收货人手机号码',
  `nation_code` varchar(128)  NULL DEFAULT NULL COMMENT '收货人区域码',
  `address` varchar(255)  NULL DEFAULT NULL COMMENT '详细地址，最大长度为228个字节',
  `country` varchar(255)  NULL DEFAULT NULL COMMENT '国家',
  `province` varchar(32)  NULL DEFAULT NULL COMMENT '省份，最大长度为32个字节',
  `city` varchar(32)  NULL DEFAULT NULL COMMENT '城市，最大长度为32个字节',
  `district` varchar(32)  NULL DEFAULT NULL COMMENT '区/县，最大长度为32个字节',
  `postal_code` varchar(32)  NULL DEFAULT NULL COMMENT '邮政编码',
  `pay_method` int(1) NULL DEFAULT 0 COMMENT '支付类型',
  `pay_no` varchar(128)  NULL DEFAULT '0' COMMENT '支付单号',
  `product_amount` int(10) NOT NULL DEFAULT 0 COMMENT '商品总金额',
  `discount` int(10) NULL DEFAULT 0 COMMENT '折扣，优惠金额',
  `order_amount` int(10) NULL DEFAULT 0 COMMENT '订单金额',
  `payment_amount` int(10) NULL DEFAULT 0 COMMENT '实付金额',
  `express_fee` int(10) NULL DEFAULT NULL COMMENT '运费金额',
  `pay_time` timestamp(0) NULL DEFAULT NULL COMMENT '实际支付时间',
  `delivery_method` int(11) NOT NULL DEFAULT 0,
  `delivery_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发货时间',
  `complete_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '交易完成时间',
  `delivery_status` int(1) NULL DEFAULT 1 COMMENT '物流状态：未发货  0|已发货    1',
  `express_type` int(1) NULL DEFAULT 1 COMMENT '物流类型 1:手动发货; 2:系统自动发货',
  `refund_type` int(1) NULL DEFAULT 1 COMMENT '退款类型：商家主动退款 MERCHANT_REFUND 1 |买家申请退款 CUSTOMER_REFUND 2',
  `refund_status` int(1) NULL DEFAULT NULL COMMENT '退款状态',
  `currency` varchar(255)  NOT NULL COMMENT '币制',
  `NAME` varchar(255)  NULL DEFAULT NULL COMMENT '实名姓名',
  `express_number` varchar(50)  NULL DEFAULT NULL COMMENT '物流单号',
  `weight_amount` int(11) NULL DEFAULT NULL COMMENT '商品总重量',
  `pay_scene` int(1) NULL DEFAULT NULL COMMENT '支付方式',
  `pay_method_type` int(1) NULL DEFAULT 0 COMMENT '支付方式：0 微信支付 1 支付宝 2 银联支付 3 信用卡支付 5 货到付款 6 线下转账',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `shop_id`(`shop_id`) USING BTREE,
  INDEX `customer_id`(`customer_id`) USING BTREE,
  INDEX `order_status`(`order_status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

/*订单明细*/
DROP TABLE IF EXISTS `shop_customer_order_item`;
CREATE TABLE `shop_customer_order_item`  (
  `id` varchar(128)  NOT NULL COMMENT '主键',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `creator` varchar(128)  NOT NULL COMMENT '创建人',
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `modifier` varchar(128)  NULL DEFAULT NULL COMMENT '修改人',
  `product_spu_id` varchar(128)  NOT NULL DEFAULT '0' COMMENT '产品SPU编号',
  `product_sku_id` varchar(128)  NOT NULL COMMENT '产品SKU编号',
  `shop_id` varchar(128)  NOT NULL COMMENT '店铺编号',
  `title` varchar(128)  NOT NULL COMMENT '商品名称',
  `price` int(10) NULL DEFAULT 0 COMMENT '单价',
  `refund_status` int(1) NULL DEFAULT 0 COMMENT '退款状态',
  `delivery_status` int(1) NULL DEFAULT 0 COMMENT '发货状态',
  `quantity` int(10) NULL DEFAULT 0 COMMENT '数量',
  `discount` int(10) NULL DEFAULT 0 COMMENT '折扣',
  `payment_price` int(10) NULL DEFAULT 0 COMMENT '实际价格',
  `customer_id` varchar(128)  NOT NULL COMMENT '顾客编号',
  `order_id` varchar(128)  NULL DEFAULT NULL COMMENT '订单编号',
  `image` text  NULL COMMENT '商品图片列表',
  `currency` varchar(255)  NULL DEFAULT 'CNY' COMMENT '币制',
  `sku_code` varchar(255)  NULL DEFAULT NULL COMMENT '商品编码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE COMMENT '订单编号',
  INDEX `customer_id`(`customer_id`) USING BTREE,
  INDEX `product_sku_id`(`product_sku_id`) USING BTREE,
  INDEX `product_spu_id`(`product_spu_id`) USING BTREE,
  INDEX `shop_id`(`shop_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

/*用户表*/
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `id` varchar(64)  NOT NULL COMMENT '主键',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `nick_name` varchar(128)  NULL DEFAULT NULL,
  `avatar_url` varchar(256)  NULL DEFAULT NULL,
  `gender` int(11) NULL DEFAULT 0 COMMENT '0：未知，1：男，2：女',
  `province` varchar(128)  NULL DEFAULT NULL,
  `city` varchar(128)  NULL DEFAULT NULL,
  `country` varchar(128)  NULL DEFAULT NULL,
  `store_id` varchar(128)  NULL DEFAULT NULL COMMENT '店铺编号',
  `nation_code` varchar(10)  NULL DEFAULT NULL COMMENT '国家代码',
  `mobile` varchar(32)  NULL DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `store_id`(`store_id`) USING BTREE,
  INDEX `open_id`(`open_id`) USING BTREE,
  INDEX `official_account_open_id`(`official_account_open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;