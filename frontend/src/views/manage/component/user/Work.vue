<template>
  <div style="background:#ECECEC; padding:30px;margin-top: 30px;margin-bottom: 30px">
    <div style="height: 450px;">
      <div style="height: 350px;background: url(../static/img/interior-3530343_1920.jpg) no-repeat center;padding: 50px">
        <div style="font-size: 55px;font-weight: 500;color: white;text-align: center;font-family: SimHei">欢迎光临！</div>
        <div style="font-size: 30px;font-weight: 500;color: white;text-align: center;font-family: SimHei">开始选购吧</div>
        <div style="height: 180px;margin-top: 100px">
          <a-card :bordered="false" hoverable style="height: 100%;box-shadow: 3px 3px 3px rgba(0, 0, 0, .2);color:#fff">
            <a-row style="padding: 50px;margin: 0 auto">
              <a-col :span="16">
                <a-row>
                  <a-col :span="18">
                    <a-input v-model="key" placeholder="搜索商家"/>
                  </a-col>
                  <a-col :span="4" :offset="2">
                    <a-button type="primary" @click="fetch">
                      查找
                    </a-button>
                  </a-col>
                  <a-col :span="24"></a-col>
                </a-row>
              </a-col>
              <a-col :span="6" :offset="2">
              </a-col>
            </a-row>
          </a-card>
        </div>
      </div>
    </div>
    <a-row :gutter="30" style="padding: 35px;margin: 0 auto">
      <a-col :span="6" v-for="(item, index) in roomList" :key="index">
        <div style="background: #e8e8e8">
          <a-carousel autoplay style="height: 200px;" v-if="item.images !== undefined && item.images !== ''">
            <div style="width: 100%;height: 150px" v-for="(item, index) in item.images.split(',')" :key="index">
              <img :src="'http://127.0.0.1:9527/imagesWeb/'+item" style="width: 100%;height: 200px">
            </div>
          </a-carousel>
          <a-card :bordered="false">
            <div slot="title">
              <div style="font-size: 14px;font-family: SimHei">
                <div>
                  <a-badge status="success" v-if="item.currentStatus === '1'" style="display: contents;margin: 0 auto"/>
                  <a-badge status="error" v-if="item.currentStatus === '0'" style="display: contents;margin: 0 auto"/>
                  {{ item.name }}  <a-tag color="green" style="font-size: 11px">{{ item.phone }}</a-tag>
                </div>
                <div style="font-size: 12px;margin-top: 4px">
                  <a-icon type="environment" />  {{ item.address }}
                </div>
              </div>
            </div>
            <template slot="actions" class="ant-card-actions">
              <a-icon key="shopping" type="shopping" @click="view(item)"/>
              <a-icon key="control" type="control" @click="customAdd(item)"/>
            </template>
          </a-card>
        </div>
      </a-col>
    </a-row>
    <Map :orderData="orderMapView.merchantInfo"
         @close="handleorderMapViewClose"
         :orderShow="orderMapView.visiable">
    </Map>
    <bulletin-add
      v-if="bulletinAdd.visiable"
      @close="handleBulletinAddClose"
      @success="handleBulletinAddSuccess"
      :bulletinData="bulletinAdd.data"
      :bulletinAddVisiable="bulletinAdd.visiable">
    </bulletin-add>
  </div>
</template>

<script>

import {mapState} from 'vuex'
import VehicleView from './VehicleView.vue'
import BulletinAdd from './CustomAdd.vue'
import Map from './Map.vue'
export default {
  name: 'Work',
  components: {Map, VehicleView, BulletinAdd},
  data () {
    return {
      bulletinAdd: {
        visiable: false,
        data: null
      },
      orderMapView: {
        merchantInfo: null,
        visiable: false
      },
      key: '',
      roomList: [],
      roomTypeList: [],
      loading: false,
      vehicleView: {
        visiable: false,
        data: null
      },
      rentView: {
        visiable: false,
        data: null
      },
      startDate: null,
      endDate: null
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  mounted () {
    this.getWorkStatusList()
  },
  methods: {
    handleBulletinAddClose () {
      this.bulletinAdd.visiable = false
    },
    handleBulletinAddSuccess () {
      this.bulletinAdd.visiable = false
      this.$message.success('新增定制家具成功')
    },
    customAdd (row) {
      this.bulletinAdd.data = row
      this.bulletinAdd.visiable = true
    },
    handlevehicleViewClose () {
      this.vehicleView.visiable = false
    },
    handlevehicleViewSuccess () {
      this.vehicleView.visiable = false
      this.$message.success('添加订单成功')
    },
    orderSuccess () {
      this.rentView.visiable = false
      this.$message.success('添加订单成功')
      this.fetch()
    },
    collectDelete (id) {
      this.$delete(`/cos/collect-info/${id}`).then((r) => {
        this.$message.success('取消收藏成功')
        this.fetch()
      })
    },
    collectAdd (roomCode) {
      let data = { userId: this.currentUser.userId, roomCode }
      this.$post(`/cos/collect-info`, data).then((r) => {
        this.$message.success('成功')
        this.fetch()
      })
    },
    view (record) {
      // if (record.currentStatus === '0') {
      //   this.$message.warn('此商家不在营业时间内')
      //   return false
      // }
      this.orderMapView.merchantInfo = record
      this.orderMapView.visiable = true
    },
    handleorderMapViewClose () {
      this.orderMapView.visiable = false
    },
    getRoomType () {
      this.$get(`/cos/vehicle-type-info/list`).then((r) => {
        this.roomTypeList = r.data.data
      })
    },
    getWorkStatusList () {
      this.$get(`/cos/order-info/selectMerchantList`, { key: this.key }).then((r) => {
        this.roomList = r.data.data
      })
    },
    fetch () {
      this.getWorkStatusList()
    }
  }
}
</script>

<style scoped>
>>> .ant-card-meta-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-meta-description {
  font-size: 12px;
  font-family: SimHei;
}
>>> .ant-divider-with-text-left {
  margin: 0;
}

>>> .ant-card-head-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-extra {
  font-size: 13px;
  font-family: SimHei;
}
.ant-carousel >>> .slick-slide {
  text-align: center;
  height: 250px;
  line-height: 250px;
  overflow: hidden;
}

</style>
