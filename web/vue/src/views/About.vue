<template>
  <div class="black-bg" v-if="modal==true">
    <div class="white-bg">
      <h4>Modal Title</h4>
      <p>내용</p>
      <button @click="modal=false">X</button>
    </div>
  </div>
  <div>
    <img src="../assets/p0.png" class="phone-img">
    <h4 @click="modal=true">{{products[0]}}</h4>
    <p>{{price[0]}}만원</p>
    <button @click="count[0]++">찜하기</button>
    <p>❤ : {{count[0]}}</p>
  </div>
</template>

<script>
export default {
  name: 'App',
  data(){
    return {
      modal : false,
      count : [0, 0, 0],
      products : ['아이폰12프로', '아이폰12', '아이폰12미니'],
      price : ['130', '110', '90'],
    }
  },
  components: {
  }
}
</script>

<style>
body {
  margin : 0
}

div {
  box-sizing: border-box;
}

.black-bg {
  width: 100%; height: 100%;
  background: rgba(0,0,0,0.5);
  position: fixed; padding: 20px;
}

.white-bg {
  width: 100%; background: white;
  border-radius: 8px;
  padding: 20px;
}

.phone-img {
  width: 100%;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>