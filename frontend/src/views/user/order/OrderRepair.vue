<template>
  <a-modal v-model="show" title="订单-维修保养" @cancel="onClose" :width="1300">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
      <a-button key="back" @click="onSubmit" type="primary">
        提交
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="orderInfo !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">订单信息</span></a-col>
        <a-col :span="6"><b>订单编号：</b>
          {{ orderInfo.code }}
        </a-col>
        <a-col :span="6"><b>订单价格：</b>
          {{ orderInfo.orderPrice ? orderInfo.orderPrice + '元' : '- -' }}
        </a-col>
        <a-col :span="6"><b>折后价格：</b>
          {{ orderInfo.afterOrderPrice ? orderInfo.afterOrderPrice + '元' : '- -' }}
        </a-col>
        <a-col :span="6"><b>会员折扣：</b>
          {{ orderInfo.discount }} 元
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="6"><b>订单积分：</b>
          {{ orderInfo.integral }}
        </a-col>
        <a-col :span="6"><b>订单状态：</b>
          <span v-if="orderInfo.status === '0'" style="color: red">未支付</span>
          <span v-if="orderInfo.status === '1'" style="color: blue">已支付</span>
          <span v-if="orderInfo.status === '2'" style="color: orange">配送中</span>
          <span v-if="orderInfo.status === '3'" style="color: green">已收货</span>
        </a-col>
        <a-col :span="6"><b>订单类型：</b>
          <span v-if="orderInfo.type === '0'">店内购买</span>
          <span v-if="orderInfo.type === '1'">配送</span>
        </a-col>
        <a-col :span="6"><b>下单时间：</b>
          {{ orderInfo.createDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="orderInfo.type === '1'">
        <a-col :span="6"><b>公里数：</b>
          {{ orderInfo.kilometre }}公里
        </a-col>
        <a-col :span="6"><b>配送价格：</b>
          {{ orderInfo.distributionPrice ? orderInfo.distributionPrice + '元' : '- -' }}
        </a-col>
        <a-col :span="6"><b>支付时间：</b>
          {{ orderInfo.payDate ? orderInfo.payDate : '- -' }}
        </a-col>
        <a-col :span="6"><b>送达时间：</b>
          {{ orderInfo.serviceDate }}
        </a-col>
      </a-row>
      <br/>
    </div>
    <br/>
    <div style="font-size: 13px;font-family: SimHei" v-if="userInfo !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">用户信息</span></a-col>
        <a-col :span="6"><b>会员编号：</b>
          {{ userInfo.code }}
        </a-col>
        <a-col :span="6"><b>用户姓名：</b>
          {{ userInfo.name ? userInfo.name : '- -' }}
        </a-col>
        <a-col :span="6"><b>邮箱地址：</b>
          {{ userInfo.mail ? userInfo.mail : '- -' }}
        </a-col>
        <a-col :span="6"><b>联系电话：</b>
          {{ userInfo.phone }}
        </a-col>
      </a-row>
      <br/>
    </div>
    <br/>
    <div style="font-size: 13px;font-family: SimHei" v-if="addressInfo !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">收货地址</span></a-col>
        <a-col :span="6"><b>收货编号：</b>
          {{ addressInfo.code }}
        </a-col>
        <a-col :span="6"><b>详细地址：</b>
          {{ addressInfo.address ? addressInfo.address : '- -' }}
        </a-col>
        <a-col :span="6"><b>联系人：</b>
          {{ addressInfo.contactPerson ? addressInfo.contactPerson : '- -' }}
        </a-col>
        <a-col :span="6"><b>联系方式：</b>
          {{ addressInfo.contactMethod }}
        </a-col>
      </a-row>
      <br/>
    </div>
    <br/>
    <div style="font-size: 13px;font-family: SimHei" v-if="merchantInfo !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">商家信息</span></a-col>
        <a-col :span="6"><b>商家编号：</b>
          {{ merchantInfo.code }}
        </a-col>
        <a-col :span="6"><b>商家名称：</b>
          {{ merchantInfo.name ? merchantInfo.name : '- -' }}
        </a-col>
        <a-col :span="6"><b>地 址：</b>
          {{ merchantInfo.address ? merchantInfo.address : '- -' }}
        </a-col>
        <a-col :span="6"><b>负责人：</b>
          {{ merchantInfo.principal }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="6"><b>联系方式：</b>
          {{ merchantInfo.phone }}
        </a-col>
      </a-row>
      <br/>
    </div>
    <br/>
    <div style="font-size: 13px;font-family: SimHei" v-if="orderItemInfo.length !== 0">
      <a-row style="padding-left: 24px; padding-right: 24px;" :gutter="[25, 25]">
        <a-col :span="24">
          <div class="section-header">
            <span class="section-title">选择维修保养家具</span>
          </div>
        </a-col>
        <a-col :span="12">
          <div class="table-container">
            <a-table :columns="columns" :data-source="orderItemInfo" :pagination="false"></a-table>
          </div>
        </a-col>
        <a-col :span="12">
          <div class="repair-type-container">
            <div class="section-header">
              <span class="section-title">我的积分：{{ integral }}</span>
            </div>
            <a-radio-group v-model="selectedRepairType" class="repair-type-list">
              <a-list :data-source="repairTypeList" size="small">
                <a-list-item slot="renderItem" slot-scope="item" class="repair-type-item">
                  <a-radio :value="item.id" class="repair-type-radio">
                    <div class="repair-type-content">
                      <span class="repair-type-name">{{ item.name }}</span>
                      <span class="repair-type-integral">{{ item.integral }}积分</span>
                    </div>
                  </a-radio>
                </a-list-item>
              </a-list>
            </a-radio-group>
            <a-textarea
              v-model="content"
              placeholder="维修保养备注"
              :rows="4"
              class="repair-remark"
            />
          </div>
        </a-col>
      </a-row>
      <br/>
    </div>
    <br/>
  </a-modal>
</template>

<script>
import moment from 'moment'
import {mapState} from 'vuex'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'orderView',
  props: {
    orderShow: {
      type: Boolean,
      default: false
    },
    orderData: {
      type: Object
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.orderShow
      },
      set: function () {
      }
    },
    columns () {
      return [{
        title: '选择',
        dataIndex: 'selected',
        customRender: (text, record, index) => {
          return <a-radio
            checked={this.selectedFurniture === record.dishesId}
            onChange={() => this.selectFurniture(record.dishesId)}
          />
        }
      }, {
        title: '家具名称',
        dataIndex: 'dishesName'
      }, {
        title: '图片',
        dataIndex: 'images',
        customRender: (text, record, index) => {
          if (!record.images) return <a-avatar shape="square" icon="user" />
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.images.split(',')[0] } />
            </template>
            <a-avatar shape="square" icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.images.split(',')[0] } />
          </a-popover>
        }
      }]
    }
  },
  data () {
    return {
      integral: 0,
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      repairInfo: null,
      reserveInfo: null,
      durgList: [],
      logisticsList: [],
      current: 0,
      userInfo: null,
      orderInfo: null,
      merchantInfo: null,
      orderItemInfo: [],
      repairTypeList: [],
      addressInfo: null,
      staffInfo: null,
      evaluateInfo: null,
      selectedRepairType: null,
      selectedFurniture: null,
      content: ''
    }
  },
  watch: {
    orderShow: function (value) {
      if (value) {
        this.dataInit(this.orderData.id)
        this.selectDetailByUserId()
      }
    },
    selectedRepairType: function (newVal) {
      if (newVal) {
        // 可以在这里处理选中维修类型后的逻辑
        console.log('选中的维修类型ID:', newVal)
      }
    }
  },
  mounted () {
    this.queryRepairType()
  },
  methods: {
    selectDetailByUserId () {
      this.$get(`/cos/user-info/detailByUserId/${this.currentUser.userId}`).then((r) => {
        this.integral = r.data.data.integral
      })
    },
    onSubmit () {
      // 校验家具选择
      if (!this.selectedFurniture) {
        this.$message.warning('请选择需要维修保养的家具')
        return false
      }

      // 校验维修类型选择
      if (!this.selectedRepairType) {
        this.$message.warning('请选择维修保养类型')
        return false
      }
      // 校验积分是否足够
      const selectedType = this.repairTypeList.find(item => item.id === this.selectedRepairType)
      if (selectedType && this.integral < selectedType.integral) {
        this.$message.warning(`积分不足，当前积分：${this.integral}，所需积分：${selectedType.integral}`)
        return false
      }
      let values = {
        userId: this.currentUser.userId,
        merchantId: this.orderInfo.merchantId,
        addressId: this.orderInfo.addressId,
        furnitureId: this.selectedFurniture,
        typeId: this.selectedRepairType,
        content: this.content
      }
      console.log(values)

      this.$post('/cos/furniture-repair-record', {
        ...values
      }).then((r) => {
        this.$emit('success')
      })
    },
    selectFurniture (furnitureId) {
      this.selectedFurniture = furnitureId
    },
    queryRepairType () {
      this.$get('/cos/repair-type-info/list').then((r) => {
        this.repairTypeList = r.data.data
      })
    },
    dataInit (orderId) {
      this.$get(`/cos/order-info/${orderId}`).then((r) => {
        this.userInfo = r.data.user
        this.orderInfo = r.data.order
        this.merchantInfo = r.data.merchant
        this.orderItemInfo = r.data.orderItem
        this.addressInfo = r.data.address
        this.staffInfo = r.data.staff
        this.evaluateInfo = r.data.evaluate
        this.imagesInit(this.merchantInfo.images)
      })
    },
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>.section-header {
  margin-bottom: 15px;
  border-bottom: 1px solid #e8e8e8;
  padding-bottom: 10px;
}

.section-title {
  font-size: 16px;
  font-weight: 650;
  color: #000c17;
}

.table-container {
  border: 1px solid #e8e8e8;
  border-radius: 4px;
  overflow: hidden;
}

.repair-type-container {
  border: 1px solid #e8e8e8;
  border-radius: 4px;
  padding: 16px;
  background-color: #fafafa;
}

.repair-type-list {
  width: 100%;
  margin-bottom: 16px;
}

.repair-type-item {
  padding: 8px 12px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.repair-type-item:hover {
  background-color: #f0f0f0;
}

.repair-type-radio {
  width: 100%;
}

.repair-type-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.repair-type-name {
  flex: 1;
  color: #262626;
}

.repair-type-integral {
  color: #ff4d4f;
  font-weight: 500;
  margin-left: 10px;
}

.repair-remark {
  border-radius: 4px;
}

.repair-remark >>> .ant-input {
  border-color: #d9d9d9;
}

.repair-remark >>> .ant-input:focus {
  border-color: #40a9ff;
  box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
}

.repair-type-radio {
  width: 100%;
  display: flex;
  align-items: flex-start;
}

.repair-type-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex: 1;
  margin-left: 8px; /* 为单选按钮和内容之间添加间距 */
}

.repair-type-name {
  flex: 1;
  color: #262626;
}

.repair-type-integral {
  color: #ff4d4f;
  font-weight: 500;
  margin-left: 10px;
  white-space: nowrap; /* 防止积分信息换行 */
}
</style>
