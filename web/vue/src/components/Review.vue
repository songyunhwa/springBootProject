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
      <button @click="putReview">등록</button>
      </tbody>
    </table>

    <ul>
      <li v-for="review in reviews" v-bind:key="review" class="review">
        <table>
          <tbody>
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
        reviews: [{
          id: '',
          placeName: '',
          placeId: '',
          userName: '',
          userId: '',
          contents: '',
          star: '',
          prevId: '',
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
      var params = {
        id: '',
        placeName: this.select_place.name,
        placeId: this.select_place.id,
        userName: this.$cookies.get('email'),
        userId: '',
        contents: this.input,
        star: '',
        prevId: ''
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
            this.reviews.forEach(place => {
              console.log(place);
            })
          })
          .catch(({error}) => {
            console.log(error);
          })

    }
    ,
    modifyReview(id, review) {
      review.contents = review.input;
      review.star = '0';
      console.log(this.url + 'review/' + id);
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
    }
    ,
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