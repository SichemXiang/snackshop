<template>
  <div class="chart-container">
    <div id="myChart123" :style="{width: '1000px', height: '550px'}"></div>
    <div class="button-wrapper">
      <button class="time-range-btn" @click="findSalesByTimeRange(0)">今天</button>
      <button class="time-range-btn" @click="findSalesByTimeRange(1)">昨天</button>
      <button class="time-range-btn" @click="findSalesByTimeRange(7)">过去7天</button>
      <button class="time-range-btn" @click="findSalesByTimeRange(30)">过去30天</button>
      <button class="time-range-btn" @click="findSalesByTimeRange(60)">过去60天</button>
      <button class="time-range-btn" @click="findSalesByTimeRange(365)">过去365天</button>
    </div>
  </div>

</template>

<script setup>
import { ref, onMounted ,watch} from "vue";
import * as echarts from 'echarts';
import axios from "@/util/axios";

const findSalesList = ref([]);
const x = ref([]);
const y = ref([]);
let myChart;  // 定义图表实例

const findSalesByTimeRange = async (days) => {
  const res = await axios.get("/goodsSales/findSalesByTimeRange/" + days);
  findSalesList.value = res.data.data;
  x.value = findSalesList.value.map(item => item.goodsName);
  y.value = findSalesList.value.map(item => item.goodsSales);

  updateChart();
}
const updateChart = () => {
  if (myChart) {
    myChart.dispose(); // 销毁旧的图表实例
  }

  myChart = echarts.init(document.getElementById("myChart123")); // 创建新的图表实例

  myChart.setOption({
    title: {
      show: true,
      text: '商品销量柱状图',
    },
    xAxis: {
      type: 'category',
      data: x.value,
      axisLabel: {
        rotate: -45
      }
    },
    yAxis: {
      type: 'value',
      axisTick: {
        show: true
      },
      axisLine: {
        symbol: ['none', 'arrow'],
        symbolSize: [0, 8],
        show: true
      },
    },
    series: [
      {
        name: "销量",
        type: "bar",
        data: y.value,
        label: {
          show: true,
          position: 'top',
          color: '#000',
          fontWeight: 'bold'
        }
      }
    ]
  });

  window.onresize = function () {
    myChart.resize();
  };
}

const totalGoods = ref(0);
const totalSales = ref(0);

const calculateSummary = () => {
  totalGoods.value = findSalesList.value.length;
  totalSales.value = findSalesList.value.reduce((sum, item) => sum + item.goodsSales, 0);
}

onMounted(() => {
  findSalesByTimeRange(7);
});

watch(findSalesList, calculateSummary);
</script>

<style lang="scss" scoped>
.chart-container {
  position: relative;
}

.button-wrapper {
  position: absolute;
  top: 100px;
  left: 1000px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}



.time-range-btn {
  padding: 8px 16px;  /* 设置按钮内边距 */
  font-size: 14px;   /* 设置按钮文字大小 */
  background-color: #007bff;  /* 设置按钮背景色 */
  color: #fff;       /* 设置按钮文字颜色 */
  border: none;      /* 移除按钮边框 */
  border-radius: 4px;  /* 设置按钮圆角 */
  cursor: pointer;   /* 设置鼠标指针样式为手型 */
  margin-right: 10px;  /* 设置按钮之间的右侧间距 */
}

.time-range-btn:hover {
  background-color: #0056b3;  /* 设置鼠标悬停时的按钮背景色 */
}

</style>
