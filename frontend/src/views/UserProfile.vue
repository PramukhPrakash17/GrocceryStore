<template>
  <div class="container">
    <!-- Main Content -->
    <main class="content">
      <div class="header">
        <img src="../assets/pleasantgrocery.jpg" class="header-image" alt="Header">
      </div>

      <div class="profile-card">
        <div class="profile-info">
          <div>
            <h2>{{ profile.name }}</h2>
          </div>
        </div>
      </div>

      <div class="details-card">
        <h3>Profile Information</h3>
        <button @click="toggleEditMode" class="edit-btn">
          {{ isEditing ? 'Save' : 'Edit' }}
        </button>
        
        <ul>
          <li><strong>Full Name:</strong> 
            <span v-if="!isEditing">{{ profile.name }}</span>
            <input v-else v-model="profile.name">
          </li>
          <li><strong>Mobile:</strong> 
            <span v-if="!isEditing">{{ profile.phoneNumber }}</span>
            <input v-else v-model="profile.phoneNumber">
          </li>
          <li><strong>Email:</strong> 
            <span v-if="!isEditing">{{ profile.email }}</span>
            <input v-else v-model="profile.email">
          </li>
          <li><strong>Address:</strong> 
            <span v-if="!isEditing">{{ profile.address }}</span>
            <input v-else v-model="profile.address">
          </li>
          <li><strong>Country:</strong> 
            <span v-if="!isEditing">{{ profile.country }}</span>
            <input v-else v-model="profile.country">
          </li>
        </ul>
      </div>
    </main>
  </div>
</template>

<script>
import axios from 'axios';
import { mapGetters} from 'vuex';

export default {
  name: "UserProfile",
  data() {
    return {
      isEditing: false, 
      profileId: this.getUserId, 
      profile: {
        name: "",    
        email: "",
        phoneNumber: "",  
        address: "",
        country: ""  
      }
    };
  },
  created() {
    this.fetchProfile();
  },
  computed: {   
      ...mapGetters(["getUserId"]), // getting the userId from the store

     },
  methods: {
    async fetchProfile() {
      try {
        const token = localStorage.getItem("authToken"); 
        const response = await axios.get(`http://localhost:5002/api/users/${this.getUserId}`, {
          headers: {
            Authorization: `Bearer ${token}`, 
          },
        });
        this.profile = response.data;
      } catch (error) {
        console.error("Error fetching profile:", error);
      }
    },
    toggleEditMode() {
      if (this.isEditing) {
        this.saveProfile();
      }
      this.isEditing = !this.isEditing;
    },
    async saveProfile() {
  try {
    const token = localStorage.getItem("authToken"); 

   
    const profileData = {
      name: this.profile.name,
      email: this.profile.email,
      phoneNumber: this.profile.phoneNumber,
      address: this.profile.address,
      country: this.profile.country
    };

    await axios.put(`http://localhost:5002/api/users/${this.getUserId}`, profileData, {
      headers: {
        Authorization: `Bearer ${token}`, 
        "Content-Type": "application/json", 
      },
    });
    console.log("Profile updated successfully");
  } catch (error) {
    console.error("Error updating profile:", error);
  }
}

  }
};
</script>

 <style scoped>
  /* Layout styles */
  .container {
    display: flex;
    height: 100vh;
  }
  

  /* Main Content */
  .content {
    flex: 1;
    padding: 20px;
    background: #f9f9f9;
  }
  
  /* Header */
  .header {
    position: relative;
    width: 100%;
  }
  
  .header-image {
    width: 100%;
    height: 400px;
    object-fit: cover;
    border-radius: 10px;
  }
  
  .header h1 {
    position: absolute;
    top: 20px;
    left: 30px;
    color: white;
    font-size: 28px;
    font-weight: bold;
  }
  
  /* Profile Card */
  .profile-card {
    display: flex;
    width: 945px;
    height:200px;
    align-items: center;
    background: white;
    padding: 30px;
    margin-top: -60px; /* Adjusted to display above the header image */
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    position: relative;
    left:30px;
    z-index: 1;

  }
  
  .profile-pic {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    margin-right: 20px;
  }
  
  /* Details Card */
  .details-card {
    margin-top: 20px;
    padding: 20px;
    background: white;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    position: relative;
    text-align: left;
  }
  
  .details-card h3 {
    margin-bottom: 10px;
    font-size: 20px;
    font-weight: bold;
    display: inline-block;
  }
  
  .edit-btn {
    float: right;
    background: #34d399;
    border: none;
    color: white;
    padding: 8px 15px;
    border-radius: 5px;
    cursor: pointer;
    font-size: 14px;
  }
  
  .edit-btn:hover {
    background: #059669;
  }
  
  .details-card ul {
    list-style: none;
    padding: 0;
  }
  
  .details-card li {
    margin: 10px 0;
  }
  
  .details-card input, 
  .details-card textarea {
    width: 100%;
    padding: 8px;
    margin-top: 5px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
  }
  
  .details-card i {
    margin-right: 10px;
    font-size: 18px;
  }

   /* Media Queries */
    @media (max-width: 1200px) {
      .profile-card {
        width: 100%;
        height: auto;
        flex-direction: column;
        align-items: flex-start;
      }

      .profile-pic {
        margin-bottom: 10px;
      }
    }

    @media (max-width: 992px) {
      .header h1 {
        font-size: 24px;
      }

      .details-card {
        padding: 15px;
      }
    }

    @media (max-width: 768px) {
      .container {
        flex-direction: column;
      }

      .content {
        padding: 15px;
      }

      .profile-card {
        margin-top: 20px;
        padding: 20px;
      }
    }

    @media (max-width: 480px) {
      .header h1 {
        font-size: 20px;
      }

      .edit-btn {
        padding: 6px 10px;
        font-size: 12px;
      }

      .details-card input, 
      .details-card textarea {
        font-size: 14px;
      }
    }
     </style>