<template>
  <div class="upload-container">
    <a-spin :spinning="loading">
      <a-row>
        <a-col :span="12">
          <a-card hoverable>
            <!--        <a-upload-->
            <!--            :before-upload="beforeUpload"-->
            <!--            :customRequest="customUpload"-->
            <!--            @change="handleChange"-->
            <!--            :file-list="fileList"-->
            <!--            action=""-->
            <!--            multiple-->
            <!--        >-->
            <a-upload
                :before-upload="beforeUpload"
                :customRequest="customUpload"
                @change="handleChange"
                action=""
                multiple
            >
              <a-button icon="upload">选择图片</a-button>
            </a-upload>
            <a-divider>已上传图片</a-divider>
            <a-row>
              <!--          <a-col-->
              <!--              :xs="24"-->
              <!--              :sm="12"-->
              <!--              :md="8"-->
              <!--              :lg="6"-->
              <!--              v-for="(file, index) in fileList"-->
              <!--              :key="index"-->
              <!--          >-->
              <!--            <a-card hoverable>-->
              <!--              <p style="height: 280px;">-->
              <!--                <img-->
              <!--                    :src="previewList[index] || file.url"-->
              <!--                    class="uploaded-image"-->
              <!--                />-->
              <!--              </p>-->
              <!--              <p>{{ file.name }}</p>-->
              <!--            </a-card>-->
              <!--          </a-col>-->
              <a-col

                  v-if="this.previewList!==''"
              >
                <a-card hoverable>
                  <p style="height: 280px;width: auto">
                    <img
                        :src="previewList"
                        class="uploaded-image"
                    />
                  </p>
                </a-card>
              </a-col>
            </a-row>
          </a-card>
        </a-col>
        <a-col :span="12">
          <a-card>
            <a-button type="primary" @click="DetectImg">检测</a-button>
            <a-button type="primary" @click="Download">下载图片</a-button>
            <a-divider>检测结果</a-divider>
            <a-col
                v-if="this.showpreview!==''"
            >
              <a-card hoverable>
                <p style="height: 280px;width: auto">
                  <img
                      :src="showpreview"
                      class="uploaded-image"
                  />
                </p>
              </a-card>
            </a-col>
          </a-card>
        </a-col>
      </a-row>
    </a-spin>
  </div>
</template>

<script>
import {defineComponent} from 'vue';
import {Detect, showImg, uploadApi} from "@/api/api";

export default defineComponent({
  data() {
    return {
      // fileList: [],
      // previewList: [],
      fileList: "",
      previewList: "",
      showpreview: "",
      loading: false
    };
  },
  mounted() {
    this.ShowImage()
  },
  methods: {
    beforeUpload(file) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
      if (!isJpgOrPng) {
        this.$message.error('只能上传 JPG/PNG 格式的图片！');
        return false;
      }
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error('图片大小不能超过 2MB！');
        return false;
      }
      return true;
    },
    handleChange(info) {
      console.log(info)
      // this.fileList = info.fileList.slice();
      this.fileList = "";
      // this.previewList = [];
      this.previewList = "";
      info.fileList.forEach((file) => {
        if (file.status !== 'uploading') {
          this.getBase64(file.originFileObj, (imageUrl) => {
            // this.previewList.push(imageUrl);
            this.previewList = imageUrl;
          });
        }
      });
    },
    getBase64(file, callback) {
      const reader = new FileReader();
      reader.addEventListener('load', () => callback(reader.result));
      reader.readAsDataURL(file);
    },
    customUpload(e) {
      uploadApi({
        file: e.file,
        onUploadProgress: (ev) => {
          // ev - axios 上传进度实例，上传过程触发多次
          // ev.loaded 当前已上传内容的大小，ev.total - 本次上传请求内容总大小
          // console.log(ev);
          const percent = (ev.loaded / ev.total) * 100;
          // 计算出上传进度，调用组件进度条方法
          e.onProgress({percent});
        },
      }).then((res) => {
        console.log('c', res.data);
        this.$message.success("上传成功")
        // 调用实例的成功方法通知组件该文件上传成功
        e.onSuccess(res.data, e);
      }).catch((err) => {
        // 调用实例的失败方法通知组件该文件上传失败
        e.onError(err);
      });
    },
    DetectImg() {
      this.loading = true
      Detect().then((res) => {
        if (res.data.code === 0) {
          this.$message.success("检测成功")
          this.ShowImage()
          this.loading = false
        } else {
          this.loading = false
        }
      }).catch((error) => {
        this.$message.error(error)
        this.loading = false
      })
    },
    ShowImage() {
      showImg().then((res) => {
        if (res.data.code === 0) {
          this.showpreview = "data:image/jpeg;base64," + res.data.data
        }
      })
    },
    // 将 base64 转换为 Blob 对象
    dataURLtoBlob(dataurl) {
      var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
          bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
      }
      return new Blob([u8arr], {type: mime});
    },
    Download() {
      // 下载 base64 格式的图片
      var imageBlob = this.dataURLtoBlob(this.showpreview);
      var imageUrl = URL.createObjectURL(imageBlob);
      var a = document.createElement('a');
      a.href = imageUrl;
      a.download = "1.jpg";
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
      URL.revokeObjectURL(imageUrl);
    }
  },
});
</script>

<style scoped>
.upload-container {
  margin: 20px;
}

.uploaded-image {
  width: 100%;
  height: 100%;
  object-fit: contain;
}
</style>
