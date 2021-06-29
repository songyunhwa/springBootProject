<template>
  <div style="float: right">
    <table>
      <thead>
      <tr>
        <th>댓글</th>
      </tr>
      </thead>
      <tbody>
      <td><textarea type="text" rows="5" style="width:500px; resize: none;" v-model="this.input"/></td>
      <input type="file" name="image" id="image"/>
      <button @click="uploadFile">등록</button>
      </tbody>
    </table>

    <ul>
      <li v-for="review in reviews" v-bind:key="review" class="review">
        <table>
          <tbody>
          <td><img v-bind:src="image_path + review.fileName"/></td>
          <td>{{ review.userName }}</td>
          <td>{{ review.contents }}</td>

          <div v-show="!review.modify">
            <button @click="onToggle(review)">수정</button>
            <button @click="deleteReview(review.id, review)">삭제</button>
          </div>
          </tbody>
        </table>


        <div v-if="review.modify">
          <div>
            <textarea type="text" rows="5" style="width:500px; resize: none;" v-model="review.input"/>
          </div>
          <div style="margin-left: 0px;">
            <button @click="modifyReview(review.id, review);onToggle(review);">수정</button>
            <button @click="deleteReview(review.id, review)">삭제</button>
          </div>
        </div>

      </li>
    </ul>
  </div>

  <Modal v-show="showModal" :select_modal="modal" @close="onToggleModal"></Modal>
</template>
<script>
import axios from "axios";
import Modal from "@/views/Modal";
export default {
  name: 'Review',
  components: {
    Modal
  },
  props: {
    select_place: Object
  },
  data: () => ({
        input: '',
        url: 'http://localhost:9000/api/v1/',
        image_path : 'C:\\Users\\pc\\Documents\\공부\\springBootProject_back\\src\\main\\resources\\file\\',
        fileId: '',
        reviews: [{
          id: '',
          placeName: '',
          placeId: '',
          userName: '',
          userId: '',
          contents: '',
          star: '',
          prevId: '',
          fileId: '',
          fileName: '',

          // 아래부터는 사용자 입력
          modify: false,
          input: '',
          input_star: '',
        }],
        showModal: false,
        modal: {
          header: '',
          body: '',
          footer: ''
        }
      }
  ),
  computed: {}
  ,
  methods: {
    putReview() {
      var image = document.getElementById("image");
      if (image.files[0]&&image.files[0].length>0) {
        this.uploadFile();
      }

      var params = {
        id: '',
        placeName: this.select_place.name,
        placeId: this.select_place.id,
        userName: this.$cookies.get('email'),
        userId: '',
        contents: this.input,
        star: '',
        prevId: '',
        fileId: this.fileId
      };
      return axios
          .post(
              this.url + 'review',
              params
          )
          .then(() => {
            this.getReview(this.select_place.id);
          })
          .catch(({error}) => {
            console.log(error);
          })

    }
    ,
    getReview(id) {
      return axios
          .get(this.url + 'review?id=' + id)
          .then((data) => {
            this.reviews = data.data;
          })
          .catch(({error}) => {
            console.log(error);
          })

    }
    ,
    modifyReview(id, review) {
      review.contents = review.input;
      review.star = '0';
      return axios
          .post(
              this.url + 'review/' + id,
              review
          )
          .then((data) => {
            console.log(data);
          })
          .catch(({error}) => {
            console.log(error);
          })
    }
    ,
    deleteReview(id, review) {
      if (review.userName !== this.$cookies.get('email')) {
        this.modal.body = '삭제 불가능합니다.'
        this.onToggleModal();
        return;
      }

      return axios
          .delete(this.url + 'review/' + id)
          .then(() => {
          })
          .catch(({error}) => {
            console.log(error);
          })
    },
    uploadFile() {
      const formData = new FormData();
      var image = document.getElementById("image");
      formData.append("image", image.files[0]);

      axios.post(this.url + 'review/image', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then((data) => {
        this.fileId = data;

      }).catch((error) => {
        console.log(error);
      })
    },
    getFile(fileId) {
      return axios.post(this.url + 'review/image?id=' + fileId, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then((data) => {
        // 파일 아이디가 들어옴 -> review fileId 에 넣어야함.
        console.log(data);
      }).catch((error) => {
        console.log(error);
      })
    },
    onToggle(review) {
      if (review.userName !== this.$cookies.get('email')) {
        this.modal.body = '수정 불가능합니다.'
        this.onToggleModal();
        return;
      }

      // 현재 input을 저장되어있던 값으로 변환
      review.input = review.contents;

      if (review.modify) {
        review.modify = false;
      } else {
        review.modify = true
      }
    },
    onToggleModal() {
      if (this.showModal) {
        this.showModal = false;
      } else {
        this.showModal = true;
      }
    },
  }
}
</script>
<style>

.review {
  list-style: none;
  color: #7C7877;
}
</style>