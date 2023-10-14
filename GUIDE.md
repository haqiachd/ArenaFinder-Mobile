<br>
<a name="nduwur-dewe"></a>
<p align="center">
 <a href="https://www.youtube.com/watch?v=t9VWICGOD90&ab_channel=HITSRecords"><img src="images/logo-c2.png" alt="Logo Kelompok C2" width="195" height="155"></a>
</p>

<h3 align="center">ArenaFinder Mobile ~ Panduan Pengguna</h3>
<p align = "center">:books: Berisi panduan pengguna tentang tata cara instalasi aplikasi ArenaFinder Mobile :stuck_out_tongue_winking_eye:</p>

---

<!-- Table of Contents -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#software-tools">Software dan Tools yang Dibutuhkan</a></li>
    <li>
    <a href="#instllation">Cara Instalasi Aplikasi</a>
      <ul>
        <li><a href="#satu">Import MySQL Database di phpMyAdmin</a></li>
        <li><a href="#dua">Clone Rest-API Mobile ArenaFinder</a></li>
        <li><a href="#tiga">Membuat Project di Android Studio</a></li>
        <li><a href="#empat">Menjalankan ArenaFinder di Android Studio</a></li>
      </ul>
    </li>
  </ol>
</details>

## Software dan Tools yang Dibutuhkan <a name = "software-tools"></a>
 - **Android Studio** <br>
 - **Browser**
 - **XAMPP**
 - **Composer**
 
Pastikan juga Laptop atau Komputer Anda terhubung ke Internet.

## Cara Instalasi Aplikasi <a name = "instllation"></a>

### 1. Import MySQL Database di phpMyAdmin <a name = "satu"></a>
Download database [ArenaFinder](https://drive.google.com/drive/folders/1c9xHuEOusnqJxNEYW4B3H-rG1FlXcvvt?usp=sharing) lalu aktifkan Apache dan MySQL pada ```XAMPP``` kemudian buka [phpMyAdmin](http://localhost/phpmyadmin/index.php) di browser, buat database baru dengan nama ```arenafinder``` dan  import file database _arenafinder.sql_ yang telah didownload, dan akhiri dengan menekan tombol ```Import```.

<p align="right">(<a href="#nduwur-dewe">Kembali ke Atas</a>)</p>

### 2. Clone Rest-API Mobile ArenaFinder <a name = "dua"></a>
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
<p align="right">(<a href="#nduwur-dewe">Kembali ke Atas</a>)</p>

### 3. Membuat Project di Android Studio <a name = "tiga"></a>
 - Buka Android Studio dan buat project baru.
 - Di bagian Template pilih ```Phone and Tablet``` dan ```Empty Activity``` lalu klik Next.
 - Isikan nama project dengan nama ```ArenaFinder``` _(optional)_
 - Pada bagian input package isikan dengan nama package sebagai berikut, lalu klik Finish.
   ```sh
   com.c2.arenafinder
   ```
 - Buka terminal di Android Studio dengan menekan shortcut ```Alt + F12```.
 - Ketikan perintah berikut untuk meng-clone repositori dari GitHub.
   ``` sh
   git clone https://github.com/haqiachd/ArenaFinder-Mobile.git cloning
   ```
 - Buka project yang baru saja Anda buat di ```File Explorer```.
 - Pindahkan semua file dan directory yang ada di dalam folder ```cloning``` ke dalam folder project Anda.
 - Selesai
<p align="right">(<a href="#nduwur-dewe">Kembali ke Atas</a>)</p>

### 4. Menjalankan ArenaFinder di Android Studio. <a name = "empat"></a>
 - Pastikan Apache dan MySQL pada XAMPP sudah aktif.
 - Pastikan juga Laptop atau Komputer Anda terhubung ke Internet.
 - Buka ```CMD``` atau ```Terminal``` untuk mendapatkan Alamat IP dengan mengetikan perintah. <br>
   #### Windows
      ```sh
      ipconfig
    ```
   #### Mac OS
      ```sh
      ifconfig | grep "inet "
    ```
 - Copy IP Address.
 - Buka class ```RetrofitClient.java``` pada package [com.c2.arenafinder.api.retrofit](https://github.com/haqiachd/ArenaFinder-Mobile/tree/main/app/src/main/java/com/c2/arenafinder/api/retrofit).
 - Paste-kan Alamat IP Anda pada attribute ```BASE_URL```. <br>
   #### Kode BASE_URL
      ```java
      // paste-kan disini
      public static final String BASE_URL = "http://ALAMAT IP/arenafinder/";
    ```
    #### Contoh Penulisan
      ```java
      // contoh penulisan kode yang benar
      public static final String BASE_URL = "http://172.16.106.67/arenafinder/";
    ```
 - Buka file ```network_security.xml``` pada folder [res/xml](https://github.com/haqiachd/ArenaFinder-Mobile/tree/main/app/src/main/res/xml).
 - Paste-kan alamat IP Anda didalam tag <domain includeSubdomains="true">alamat ip</domain>
   #### Kode XML
      ```xml
      <!-- paste-kan disini -->
      <domain includeSubdomains="true">ALAMAT IP</domain>
    ```
    #### Contoh Penulisan
      ```xml
      <!-- contoh penulisan kode yang benar -->
      <domain includeSubdomains="true">172.16.106.67</domain>
    ```
 - Jalankan project dengan menekan tobol ```Run App``` atau dengan shortcut ```Shift + F10```.
 - Selesai.
<p align="right">(<a href="#nduwur-dewe">Kembali ke Atas</a>)</p>
