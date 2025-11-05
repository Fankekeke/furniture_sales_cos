<template>
  <a-modal v-model="show" title="维修处理" @cancel="onClose" :width="1200">
    <template slot="footer">
      <a-button key="back" @click="checkDealer" type="primary">
        分配
      </a-button>
      <a-button @click="onClose">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="orderData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;" :gutter="15">
        <a-col :span="24">
          <a-col style="margin-bottom: 15px">
            <span style="font-size: 15px;font-weight: 650;color: #000c17">维修信息</span>
          </a-col>
          <a-col :span="24">
            <p>维修单号: {{ orderData.code }}</p>
            <p>用户名称: {{ orderData.userName }}</p>
            <p>维修类型: {{ orderData.furnitureType }}</p>
            <p>维修分类: {{ orderData.repairTypeName }}</p>
            <p>地址: {{ orderData.province }}{{ orderData.city }}{{ orderData.area }}{{ orderData.address }}</p>
            <p>创建时间: {{ orderData.createDate }}</p>
          </a-col>
        </a-col>
        <br/>
        <a-col :span="6">
          <a-col style="margin-bottom: 15px">
            <span style="font-size: 15px;font-weight: 650;color: #000c17">选择维修员</span>
          </a-col>
          <a-select v-model="staffId" style="width: 100%;">
            <a-select-option v-for="(item, index) in staffList" :value="item.id" :key="index">{{ item.name }}</a-select-option>
          </a-select>
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
      staffId: null,
      staffList: []
    }
  },
  mounted () {
    this.selectStaffList()
  },
  methods: {
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
      this.$get(`/cos/staff-info/list`).then((r) => {
        this.staffList = r.data.data
      })
    },
    checkDealer () {
      if (this.staffId === null) {
        this.$message.warn('请选择配送员工')
        return false
      }
      this.$get(`/cos/order-info/checkDealer`, {orderCode: this.orderInfo.code, staffId: this.staffId}).then((r) => {
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

<style scoped>

</style>
