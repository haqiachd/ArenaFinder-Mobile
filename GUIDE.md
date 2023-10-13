
### Aplikasi dan Tools yang Dibutuhkan
 - **Android Studio**
 - **Browser**
 - **XAMPP**
 - **Composer**


### Import MySQL Database di phpMyAdmin
 - Download database ArenaFinder [disini.](https://drive.google.com/drive/folders/1c9xHuEOusnqJxNEYW4B3H-rG1FlXcvvt).
 - Buka XAMPP, dan aktifkan `Apache` dan `MySQL`.
 - Buka [phpMyAdmin](http://localhost/phpmyadmin/index.php) pada browser.
 - Buat database baru dengan nama `arenafinder`.
 - Kemudian buka tab Import pada phpMyAdmin pada database arenafinder.
 - Tekan tombol Choose File, lalu pilih database `arenafinder.sql` yang telah didownload.
 - Kemudian kilk tombol `Import`.
 - Selesai

### Clone Rest-Api Mobile ArenaFinder
 - Buka directory ```htdocs``` pada XAMPP.
 - Buka Git Bash Anda.
 - Ketik perintah berikut untuk meng-clone repositori dari GitHub.
   <br>
   ``` sh
   git clone https://github.com/haqiachd/ArenaFinder-Mobile-RestApi.git
   ```
 - Ketikan kode dibawah ini untuk menginstall packages yang diperlukan.
   ``` sh
   composer install
   ```
 - Ubah nama folder hasil clone github menjadi ```arenafinder```.
 - Selesai.

### Membuat Project di Android Studio
 - Buka Android Studio dan buat project baru.
 - Di bagian Template pilih ```Phone and Tablet``` dan ```Empty Activity``` lalu klik next.
 - Kemudian akan muncul pop-up pengisian informasi project.
 - Isikan nama project dengan nama ```ArenaFinder``` (optional)
 - Pada bagian input package diisi dengan nama package sebagai berikut.
   ``` sh
   com.c2.arenafinder
   ```
 - Klik Finish dan tunggu hingga instalasi Gradle selesai.
 - Buka terminal di Android Studio dengan menekan tombol ```Alt + F12```.
 - Ketik perintah berikut untuk meng-clone repositori dari GitHub.
   ``` sh
   git clone https://github.com/haqiachd/ArenaFinder-Mobile.git cloning
   ```
 - Buka project yang baru saja Anda buat di ```File Explorer```.
 - Pindahkan semua file dan directory yang ada di dalam folder ```cloning``` ke dalam folder project Anda.
 - Selesai

### Menjalankan ArenaFinder di Android Studio.
 - Pastikan Apache dan MySQL pada XAMPP sudah aktif.
 - Pastikan juga Laptop atau Komputer Anda terhubung ke Internet.
 - Buka CMD atau Terminal untuk mendapatkan alamat IP dan ketikan kode. <br>
  * #### Windows
      ```sh
      ipconfig
    ```
  * #### Mac OS
      ```sh
      ifconfig | grep "inet "
    ```
 - Copy IP Address.
 - Buka class RetrofitClient.java pada package [com.c2.arenafinder.api.retrofit](https://github.com/haqiachd/ArenaFinder-Mobile/tree/main/app/src/main/java/com/c2/arenafinder/api/retrofit).
 - Paste-kan alamat IP Anda pada attribute BASE_URL. <br>
      ```java
      // paste-kan disini
      public static final String BASE_URL = "http://ALAMAT IP/arenafinder/";

      // contoh
      public static final String BASE_URL = "http://192.168.155.152/arenafinder/";
    ```
 - Buka file network_security.xml pada folder res/xml.
 - Paste-kan alamat IP Anda didalam tag <domain includeSubdomains="true">alamat ip</domain>
      ```xml
      <!-- paste-kan disini -->
      <domain includeSubdomains="true">192.168.155.152</domain>  
    ```
 - Jalankan project dengan menekan tobol ```Run App``` atau dengan shortcut ```Shift + F10```.
 - Selesai.
