<template>
  <div class="container mx-auto">
    <div class="top-0 right-0 absolute hidden md:block">
      <img :src="subtractVector" alt="Subtract Vector">
    </div>

    <div class="profile profile-setting-container">
      <div class="profile-left sm:flex-row ">
        <h1 class="setting-container-title"><strong>Profile information</strong></h1>
        <p class="setting-container-subtitle">Update your account information and email address</p>
      </div>
      <div class="profile-right bg-white relative md:w-4/12">
        <div class="profile-info">
          <success-alert v-if="hasUpdatedProfile" @onClose="hasUpdatedProfile = false"
                         message="Successfully updated profile information" category="success"></success-alert>

          <div v-for="(error, index) in errors.profile" :key="index">
            <p class="text-red-500">{{ error }}</p>
          </div>

          <form class="profile-form">
            <div class="space-y-2 mb-4">
              <label class="profile-label">First name</label>
              <input class="profile-input" type="text" v-model="user.firstName" :disabled="loadingUserData">
            </div>
            <div class="space-y-2 mb-4">
              <label class="profile-label">Last name</label>
              <input class="profile-input" type="text" v-model="user.lastName" :disabled="loadingUserData">
            </div>
            <div class="space-y-2 mb-4">
              <label class="profile-label">Email</label>
              <input class="profile-input" type="email" v-model="user.email" :disabled="loadingUserData">
            </div>
            <button class="btn btn-red save-profile-button" @click.prevent="updateProfile">Save</button>
          </form>
        </div>
      </div>
    </div>

    <div class="password profile-setting-container">
      <div class="password-left">
        <h1 class="setting-container-title"><strong>Change password</strong></h1>
        <p class="setting-container-subtitle">Make sure your account uses a long, random password to stay secure</p>
      </div>

      <div class="password-right md:w-4/12">
        <div class="password-info bg-white">

          <success-alert v-if="hasUpdatedPassword" @onClose="hasUpdatedPassword = false"
                         message="Successfully updated password" category="success"></success-alert>

          <div v-for="(error, index) in errors.password" :key="index">
            <p class="text-red-500">{{ error }}</p>
          </div>

          <form class="password-form justify-items-end">
            <div class="relative space-y-2 mb-4">
              <label class="password-label" for="new-password">New password</label>
              <input class="password-input" type="password" placeholder="*********" v-model="newPassword"
                     id="new-password">
              <div class="password-icon">
                <button @click.prevent="showPassword()" class="ml-3">
                  <img :src="show ? EyeClose : EyeOpen" alt="Password icon">
                </button>
              </div>
            </div>
            <div class="space-y-2 mb-4">
              <label class="password-label" for="new-password-confirmation">Repeat password</label>
              <input class="password-input" type="password" placeholder="*********" v-model="newPasswordConfirmation"
                     id="new-password-confirmation">
            </div>

            <button class="btn btn-red save-password-button" @click.prevent="validatePassword">Save</button>
          </form>
        </div>
      </div>
    </div>

    <!--    <div class="profile-picture profile-setting-container">-->
    <!--      <div class="profile-picture-left">-->
    <!--        <h1 class="setting-container-title"><strong>Change Profile Picture</strong></h1>-->
    <!--        <p class="setting-container-subtitle">Update your profile picture</p>-->
    <!--      </div>-->

    <!--      <div class="profile-picture-right md:w-4/12">-->
    <!--        <div class="profile-picture bg-white">-->
    <!--          <div class="profile-picture-container">-->
    <!--            <div class="flex justify-start">-->
    <!--              <p>Profile photo</p>-->
    <!--            </div>-->
    <!--            <div class="info flex justify-between flex items-center">-->
    <!--              <input type="file" id="photo" @change="onFileChange" hidden>-->
    <!--              <label class="bg-white border-2 border-gray-200 hover:bg-gray-100 rounded text-black p-3 cursor-pointer"-->
    <!--                     for="photo">Upload new photo</label>-->

    <!--              <img :src="this.user.profilePicture" alt="image-not-found" class="rounded-full h-20 w-20 object-cover">-->
    <!--            </div>-->
    <!--            <button class="btn btn-red">Update</button>-->
    <!--          </div>-->
    <!--        </div>-->
    <!--      </div>-->
    <!--    </div>-->


  </div>
</template>

<script>
import subtractVector from '@/assets/svg/vorm1.svg'
import ERROR_MESSAGES from "@/assets/js/utils/errorMessages"
import {CryptoHelper} from "@/assets/js/utils/cryptoHelper";
import SuccessAlert from "@/components/includes/alerts/successAlert.vue";
import EyeOpen from "@/assets/svg/eye-open.svg";
import EyeClose from "@/assets/svg/eye-close.svg";

export default {
  name: "Profile",
  inject: ["usersService", "authService"],
  components: {
    SuccessAlert
  },
  data() {
    return {
      show: false,
      EyeOpen: EyeOpen,
      EyeClose: EyeClose,
      subtractVector: subtractVector,
      imagePreview: "https://ui-avatars.com/api/?background=random&name=John+Doe",
      errors: {
        profile: [],
        password: []
      },
      hasUpdatedProfile: false,
      hasUpdatedPassword: false,
      user: {
        firstName: null,
        lastName: null,
        oldEmail: null,
        email: null,
        profilePicture: null,
      },
      newPassword: "",
      newPasswordConfirmation: "",
      loadingUserData: true,
    };
  },
  async created() {
    try {
      const fullName = this.authService.getFullName();
      const email = this.authService.getEmail();
      let name = fullName.split(" ");

      this.user.firstName = name[0];
      this.user.lastName = name[1];
      this.user.oldEmail = email;
      this.user.email = email;

      // if (profilePicture === "" || profilePicture === null) this.user.profilePicture = this.imagePreview;
      // else this.user.profilePicture = profilePicture;

      this.loadingUserData = false;
    } catch (err) {
      console.error(err);
    }
  },
  methods: {
    onFileChange(e) {
      const file = e.target.files[0];
      this.imagePreview = URL.createObjectURL(file);
      this.user.profilePicture = this.imagePreview;

      this.uploadImage(e)
    },
    uploadImage() {
      // let toUploadFile = this.$refs.uploadImage.files[0];
      // this.formData = new FormData();
      // this.formData.append("file", toUploadFile);
    },
    async saveProfilePicture() {
      // await this.usersService.updateProfilePicture(this.formData)
    },
    async validatePassword() {
      if (this.newPassword && this.newPasswordConfirmation == null) this.errors.password.push(ERROR_MESSAGES.PASSWORD_LENGTH_OF_8);

      // Clear errors, if any previous error exists.
      this.errors.password = [];
      if (this.newPassword.length < 8 || this.newPasswordConfirmation.length < 8) this.errors.password.push(ERROR_MESSAGES.PASSWORD_LENGTH_OF_8)
      if (this.newPassword !== this.newPasswordConfirmation) this.errors.password.push(ERROR_MESSAGES.PASSWORD_DOES_NOT_MATCH);

      // Only update password, if no errors occur
      if (this.errors.password.length === 0 && this.newPassword === this.newPasswordConfirmation) {
        let hashedPassword = CryptoHelper.hash(10, this.newPassword);
        await this.usersService.updatePassword(this.user.email, {
          email: this.user.email,
          newPassword: hashedPassword
        });

        // Clear password
        this.newPassword = "";
        this.newPasswordConfirmation = "";

        // Disable fields to prevent any changes while updating
        this.hasUpdatedPassword = true;
      }
    },
    async updateProfile() {
      this.errors.profile = [];

      if (this.user.firstName.length === 0) this.errors.profile.push(ERROR_MESSAGES.FIRSTNAME_REQUIRED)
      if (this.user.lastName.length === 0) this.errors.profile.push(ERROR_MESSAGES.LASTNAME_REQUIRED)
      if (this.user.email.length === 0) this.errors.profile.push(ERROR_MESSAGES.EMAIL_REQUIRED)

      if (this.errors.profile.length === 0) {
        try {

          const emailExists =  await this.emailExists();

          if (emailExists) {
            this.errors.profile.push(ERROR_MESSAGES.EMAIL_ALREADY_EXISTS)
            return;
          }

          const profilePicture = `https://ui-avatars.com/api/?name=${this.user.firstName}+${this.user.lastName}`;
          const response = await this.usersService.updateProfile(this.user.oldEmail, {
            email: this.user.email,
            firstName: this.user.firstName,
            lastName: this.user.lastName,
            profilePicture: profilePicture
          });

          const data = response.data
          this.authService.saveTokenIntoBrowserStorage(data);

          // Update old email
          this.user.oldEmail = this.authService.getEmail();

          // Disable fields to prevent any changes while updating
          this.hasUpdatedProfile = true;
        } catch (error) {
          console.error(error)
        }

      }
    },
    async emailExists() {
      try {
        const emailResponse = await this.usersService.getUserByEmail(this.user.email)
        const data = emailResponse.data

        if (this.user.oldEmail === data.email) return false
      } catch (error) {
        return false
      }
      return true
    },
    showPassword() {
      const el = document.querySelector("#new-password");
      const el2 = document.querySelector("#new-password-confirmation");
      if (!this.show) {
        el.setAttribute("type", "text");
        el2.setAttribute("type", "text");
        this.show = true;
      } else {
        el.setAttribute("type", "password");
        el2.setAttribute("type", "password");
        this.show = false;
      }
    }
  }
}
</script>

<style scoped>
.password-icon {
  position: absolute;
  top: 62%;
  right: 5%;
  transform: translate(-50%, -50%);
}

form {
  @apply p-2;
}

.profile label, .password label {
  display: block;
  padding-left: 15px;
  text-indent: -15px;
}

.password-right,
.profile-right,
.profile-picture-right {
  box-shadow: 0 15px 30px 0 rgba(83, 83, 83, 0.15);
}

.profile-info,
.password-info,
.profile-picture {
  @apply rounded p-8;
}

input {
  @apply border;
}

.btn {
  @apply font-bold py-2 px-4 rounded ease-in-out duration-500 text-white;
}

.btn-red {
  @apply bg-municipality-500;
}

.btn-red:hover {
  @apply bg-municipality-800
}

.profile-setting-container {
  @apply flex flex-col sm:flex-row justify-between mt-16 m-3 p-4
}

.profile-input, .password-input {
  @apply w-full text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-500;
}

.profile-label, .password-label {
  @apply font-medium text-gray-700 tracking-wide;
}

.setting-container-title {
  @apply text-3xl mb-4
}

.setting-container-subtitle {
  @apply mb-3 text-subtext-500;
}
</style>