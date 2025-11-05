<template>
  <a-modal v-model="show" title="维修处理" @cancel="onClose" :width="1000">
    <template slot="footer">
      <a-button key="back" @click="checkDealer" type="primary">
        分配
      </a-button>
      <a-button @click="onClose">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="orderData !== null">
      <div style="padding-left: 24px;padding-right: 24px;margin-bottom: 50px;margin-top: 50px">
        <a-steps :current="current" progress-dot size="small">
          <a-step title="已提交" />
          <a-step title="未派发" />
          <a-step title="维修中" />
          <a-step title="已完成" />
        </a-steps>
      </div>
      <a-row style="padding-left: 24px;padding-right: 24px;" :gutter="15">
        <a-col :span="24">
          <a-col style="margin-bottom: 15px; border-bottom: 1px solid #e8e8e8; padding-bottom: 10px;">
            <span style="font-size: 16px; font-weight: 650; color: #000c17">维修信息</span>
          </a-col>
          <a-col :span="24">
            <a-row :gutter="16">
              <a-col :span="12">
                <div class="info-item">
                  <span class="info-label">维修单号：</span>
                  <span class="info-value">{{ orderData.code }}</span>
                </div>
                <div class="info-item">
                  <span class="info-label">用户名称：</span>
                  <span class="info-value">{{ orderData.userName }}</span>
                </div>
                <div class="info-item">
                  <span class="info-label">家具名称：</span>
                  <span class="info-value">{{ orderData.dishesName }}</span>
                </div>
                <div class="info-item">
                  <span class="info-label">维修分类：</span>
                  <span class="info-value">{{ orderData.repairTypeName }}</span>
                </div>
              </a-col>
              <a-col :span="12">
                <div class="info-item">
                  <span class="info-label">地址：</span>
                  <span class="info-value">{{ orderData.province }}{{ orderData.city }}{{ orderData.area }}{{ orderData.address }}</span>
                </div>
                <div class="info-item">
                  <span class="info-label">创建时间：</span>
                  <span class="info-value">{{ orderData.createDate }}</span>
                </div>
                <div class="info-item" v-if="orderData.name">
                  <span class="info-label">维修员工：</span>
                  <span class="info-value">{{ orderData.name }}</span>
                </div>
                <div class="info-item" v-if="orderData.merchantName">
                  <span class="info-label">商家名称：</span>
                  <span class="info-value">{{ orderData.merchantName }}</span>
                </div>
              </a-col>
            </a-row>

            <a-row :gutter="16">
              <a-col :span="24">
                <div class="info-item info-item-full">
                  <span class="info-label">维修内容：</span>
                  <span class="info-value">{{ orderData.content }}</span>
                </div>
              </a-col>
            </a-row>
          </a-col>
        </a-col>
        <br/>
        <a-col :span="24">
          <a-col style="margin-bottom: 15px; border-bottom: 1px solid #e8e8e8; padding-bottom: 10px;">
            <span style="font-size: 16px; font-weight: 650; color: #000c17">选择上门维修员工</span>
          </a-col>
          <div v-if="selectedStaffInfo" class="selected-staff-info">
            <div class="section-header">
              <span class="section-title">已选员工</span>
            </div>
            <div class="staff-detail-card">
              <div class="staff-details">
                <div class="staff-detail-item">
                  <span class="detail-label">姓名：</span>
                  <span class="detail-value">{{ selectedStaffInfo.name }}</span>
                </div>
              </div>
            </div>
          </div>
          <div class="staff-list-container">
            <div
              v-for="(item, index) in staffList"
              :key="index"
              class="staff-avatar-item"
              @click="assignStaff(item.id)"
            >
              <a-avatar
                :src="item.images ? 'http://127.0.0.1:9527/imagesWeb/' + item.images : undefined"
                icon="user"
                size="large"
                :class="{ 'selected': staffId === item.id }"
              />
              <div class="staff-name">{{ item.name }}</div>
            </div>
          </div>
        </a-col>
      </a-row>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
import {mapState} from 'vuex'
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
moment.locale('zh-cn')
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'OrderAudit',
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
      }, {
        title: '购买数量',
        dataIndex: 'amount'
      }, {
        title: '单价',
        dataIndex: 'unitPrice'
      }, {
        title: '总价格',
        dataIndex: 'totalPrice',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }]
    }
  },
  watch: {
    orderShow: function (value) {
      if (value) {
        this.current = Math.abs(this.orderData.status)
      }
    }
  },
  data () {
    return {
      formItemLayout,
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
      addressInfo: null,
      staffInfo: null,
      evaluateInfo: null,
      selectedStaffInfo: null,
      staffId: null,
      staffList: []
    }
  },
  mounted () {
    this.selectStaffList()
  },
  methods: {
    assignStaff (staffId) {
      this.staffId = staffId
      this.selectedStaffInfo = this.staffList.find(staff => staff.id === staffId)
    },
    moment,
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
    selectStaffList () {
      this.$get(`/cos/staff-info/selectStaffByMerchant/${this.currentUser.userId}`).then((r) => {
        this.staffList = r.data.data
      })
    },
    checkDealer () {
      if (this.staffId === null) {
        this.$message.warn('请选择配送员工')
        return false
      }
      this.$get(`/cos/furniture-repair-record/repairDistribute`, {repairId: this.orderData.id, staffId: this.staffId}).then((r) => {
        this.$emit('success')
      })
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
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    submit () {
      console.log(this.takeShop)
      console.log(this.returnShop)
      if (this.takeShop !== '' && this.returnShop !== '') {
        this.$put(`/cos/order-info`, {
          'takeShop': this.takeShop,
          'returnShop': this.returnShop,
          'id': this.orderInfo.id
        }).then((r) => {
          this.$emit('success')
        })
      } else {
        this.$message.warn('请选择车店')
      }
    },
    onClose () {
      this.$emit('close')
    },
    cleanData () {
      this.staffCheck = []
      this.driverCheck = []
    }
  }
}
</script>

<style scoped>.info-item {
  margin-bottom: 12px;
  padding: 8px 12px;
  background-color: #fafafa;
  border-radius: 4px;
  transition: all 0.3s;
}

.info-item:hover {
  background-color: #f0f0f0;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

.info-item-full {
  padding: 12px;
}

.info-label {
  font-weight: 600;
  color: #595959;
  min-width: 80px;
  display: inline-block;
}

.info-value {
  color: #262626;
  line-height: 1.5;
}

/* 响应式优化 */
@media (max-width: 768px) {
  .info-label {
    min-width: 60px;
  }
}

.staff-list-container {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  max-height: 300px;
  overflow-y: auto;
  padding: 8px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
}

.staff-avatar-item {
  text-align: center;
  cursor: pointer;
  transition: all 0.3s;
  padding: 8px;
  border-radius: 4px;
}

.staff-avatar-item:hover {
  background-color: #f0f0f0;
}

.staff-avatar-item.selected {
  background-color: #e6f7ff;
  border: 1px solid #1890ff;
}

.staff-name {
  font-size: 12px;
  margin-top: 4px;
  color: #595959;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 60px;
}

.staff-list-container {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
  max-height: 300px;
  overflow-y: auto;
  padding: 12px;
  border: 1px solid #d9d9d9;
  border-radius: 6px;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.staff-avatar-item {
  text-align: center;
  cursor: pointer;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  padding: 12px 8px;
  border-radius: 8px;
  width: 80px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.staff-avatar-item:hover {
  background-color: #f0f7ff;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.15);
}

.staff-avatar-item.selected {
  background-color: #e6f7ff;
  border: 2px solid #1890ff;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.2);
}

.staff-name {
  font-size: 13px;
  margin-top: 8px;
  color: #595959;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 70px;
  font-weight: 500;
}

.staff-avatar-item:hover .staff-name {
  color: #1890ff;
}

.staff-avatar-item.selected .staff-name {
  color: #1890ff;
  font-weight: 600;
}

.selected-staff-info {
  margin-top: 20px;
  padding: 16px;
  background-color: #fafafa;
  border-radius: 6px;
}

.section-header {
  margin-bottom: 12px;
}

.section-title {
  font-size: 15px;
  font-weight: 600;
  color: #000c17;
  border-bottom: 1px solid #e8e8e8;
  padding-bottom: 8px;
  display: inline-block;
}

.staff-detail-card {
  display: flex;
  align-items: center;
  padding: 12px;
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
}

.staff-avatar {
  margin-right: 16px;
}

.staff-details {
  flex: 1;
}

.staff-detail-item {
  margin-bottom: 6px;
  display: flex;
}

.staff-detail-item:last-child {
  margin-bottom: 0;
}

.detail-label {
  font-weight: 600;
  color: #595959;
  min-width: 50px;
}

.detail-value {
  color: #262626;
  flex: 1;
}
</style>
