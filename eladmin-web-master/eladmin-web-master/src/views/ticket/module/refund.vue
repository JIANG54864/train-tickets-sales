<template>
  <div style="display: inline-block">
    <el-dialog :visible.sync="dialog" :close-on-click-modal="false" :before-close="cancel" :title="title" append-to-body width="550px" @close="cancel">
      <el-form ref="form" :model="form" :rules="rules" size="small" label-width="110px">
        <el-form-item label="验证身份证号" prop="passengerIdcard">
          <el-input v-model="form.passengerIdcard" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="验证姓名" prop="name">
          <el-input v-model="form.name" style="width: 370px;" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="cancel">取消</el-button>
        <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import store from '@/store'
import { updatePass } from '@/api/system/user'
export default {
  data() {
    return {
      loading: false, dialog: false, title: '验证身份', form: { passengerIdcard: '', name: '' },
      rules: {
        passengerIdcard: [
          { required: true, message: '请输入乘车人身份证号', trigger: 'blur' },
          { min: 18, max: 18, message: '长度为18位字符', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入乘车人姓名', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    cancel() {
      this.resetForm()
    },
    doSubmit() {
      this.$notify({
        title: '退票成功!',
        type: 'success',
        duration: 1500
      })
      this.dialog = false
    },
    resetForm() {
      this.dialog = false
      this.$refs['form'].resetFields()
      this.form = { newDate: '' }
    }
  }
}
</script>

<style scoped>

</style>
