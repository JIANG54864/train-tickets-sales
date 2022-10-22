<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input v-model="query.start" clearable size="small" placeholder="输入出发站台搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-input v-model="query.end" clearable size="small" placeholder="输入终点站台搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation />
      </div>
      <crudOperation :permission="permission" />
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="name" label="车次编号" />
        <el-table-column prop="start" label="出发站台" />
        <el-table-column prop="end" label="终点站台" />
        <el-table-column prop="starttime" label="开车时间" />
        <el-table-column prop="endtime" label="到达时间" />
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudTrain from '@/api/train'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { idtrain: null, start: null, end: null, name: null, starttime: null, endtime: null }
export default {
  name: 'Train',
  components: { pagination, crudOperation, rrOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '车次管理', url: 'api/train', idField: 'idtrain', sort: 'idtrain,desc', crudMethod: { ...crudTrain },
      optShow: {
        add: false,
        edit: false,
        del: false,
        reset: true
      }})
  },
  data() {
    return {}
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
