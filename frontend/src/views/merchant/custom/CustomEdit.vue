<template>
  <a-modal v-model="show" title="定制家具" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button v-if="customInfo.status == 0" key="submit" type="primary" :loading="loading" @click="handleSubmit(1)">
        接单
      </a-button>
      <a-button v-if="customInfo.status == 0" key="submit" type="danger" :loading="loading" @click="handleSubmit(5)">
        驳回
      </a-button>
      <a-button v-if="customInfo.status == 1" key="submit" type="primary" :loading="loading" @click="handleSubmit(2)">
        定制完成
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="24">
          <a-form-item label='定制要求' v-bind="formItemLayout">
            <a-textarea disabled :rows="6" v-decorator="[
            'content',
             { rules: [{ required: true, message: '请输入定制要求!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='参考图片' v-bind="formItemLayout">
            <a-upload
              disabled
              name="avatar"
              action="http://127.0.0.1:9527/file/fileUpload/"
              list-type="picture-card"
              :file-list="fileList"
              @preview="handlePreview"
              @change="picHandleChange"
            >
              <div v-if="fileList.length < 8">
                <a-icon type="plus" />
                <div class="ant-upload-text">
                  Upload
                </div>
              </div>
            </a-upload>
            <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
              <img alt="example" style="width: 100%" :src="previewImage" />
            </a-modal>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='定制价格' v-bind="formItemLayout">
            <a-input-number :disabled="customInfo.status > 0" style="width: 100%" v-decorator="[
            'orderPrice',
            { rules: [{ required: true, message: '请输入定制价格!' }] }
            ]" :min="0.1" :step="0.1"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='接单备注' v-bind="formItemLayout">
            <a-input :disabled="customInfo.status > 0" style="width: 100%" v-decorator="[
            'remark',
            { rules: [{ required: true, message: '请输入接单备注!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24" v-if="customInfo.status == 1">
          <a-form-item label='选择配送员' v-bind="formItemLayout">
            <a-select v-decorator="[
            'staffId',
             { rules: [{ required: true, message: '请选择配送员!' }] }
            ]">
              <a-select-option v-for="(item, index) in staffList" :value="item.id" :key="index">{{ item.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'BulletinEdit',
  props: {
    bulletinEditVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.bulletinEditVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      customInfo: null,
      rowId: null,
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      staffList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  mounted () {
    this.selectStaffList()
  },
  methods: {
    selectStaffList () {
      this.$get(`/cos/staff-info/selectStaffByMerchant/${this.currentUser.userId}`).then((r) => {
        this.staffList = r.data.data
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
    setFormValues ({...bulletin}) {
      this.customInfo = bulletin
      this.rowId = bulletin.id
      let fields = ['title', 'content', 'remark', 'orderPrice', 'type']
      let obj = {}
      Object.keys(bulletin).forEach((key) => {
        if (key === 'images') {
          this.fileList = []
          this.imagesInit(bulletin['images'])
        }
        if (key === 'rackUp' || key === 'type') {
          bulletin[key] = bulletin[key].toString()
        }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = bulletin[key]
        }
      })
      this.form.setFieldsValue(obj)
    },
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit (status) {
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        if (image.response !== undefined) {
          images.push(image.response)
        } else {
          images.push(image.name)
        }
      })
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        values.status = status
        values.images = images.length > 0 ? images.join(',') : null
        if (!err) {
          this.loading = true
          this.$put('/cos/custom-order-info', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
