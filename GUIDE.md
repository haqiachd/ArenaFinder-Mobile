
Aplikasi dan Tools yang dibutuhkan
 - Android Studio
 - Browser
 - XAMPP
 - Composer

Import MySQL Database di phpMyAdmin
1. Download database ArenaFinder disini.
2. Buka XAMPP, dan aktifkan Apache dan MySQL.
3. Buka phpMyAdmin pada browser.
4. Buat database baru dengan nama arenafinder.
5. Kemudian buka tab import pada phpMyAdmin pada database arenafinder.
6. Tekan tombol choose file, lalu pilih database arenafinder.sql yang telah didownload.
7. Kemudian kilk tombol import.
8. Selesai.


Clone Rest-Api Mobile ArenaFinder
1. Buka directory htdocs pada XAMPP.
2. Buka Git Bash Anda.
3. Ketik perintah berikut untuk meng-clone repositori dari GitHub. 
   -> git clone https://github.com/haqiachd/ArenaFinder-Mobile-RestApi.git
4. Ketikan kode dibawah ini untuk menginstall packages yang diperlukan.
   -> composer install
5. Ubah nama folder hasil clone github menjadi arenafinder.
6. Selesai.


Membuat Project di Android Studio
1. Buka Android Studio dan buat project baru.
2. Di bagian Template pilih Phone and Tablet dan Empty Activity lalu klik next.
3. Kemudian akan muncul pop-up pengisian informasi project.
4. Isikan nama project dengan nama ArenaFinder (optional)
5. Pada bagian input package diisi dengan nama package sebagai berikut.
   -> com.c2.arenafinder
6. Klik Finish dan tunggu hingga instalasi Gradle selesai.
7. Buka terminal di Android Studio dengan menekan tombol Alt + F12.
8. Ketik perintah berikut untuk meng-clone repositori dari GitHub. 
   -> git clone https://github.com/haqiachd/ArenaFinder-Mobile.git cloning
9. Buka project yang baru saja Anda buat di "File Explorer."
10. Pindahkan semua file dan directory yang ada di dalam folder cloning ke dalam folder project Anda.
10. Selesai

Menjalankan ArenaFinder di Android Studio.
1. Pastikan Apache dan MySQL pada XAMPP sudah aktif.
2. Pastikan juga Laptop atau Komputer Anda terhubung ke Internet.
3. Buka CMD atau Terminal untuk mendapatkan alamat IP dan ketikan kode.
   -> Windows
      -> ipconfig
   -> Mac OS
      -> ifconfig | grep "inet "
5. Copy IP Address.
6. Buka class RetrofitClient.java pada package com.c2.arenafinder.api.retrofit.
7. Paste-kan alamat IP Anda pada attribute BASE_URL.
   Contoh : 
8. Buka file network_security.xml pada folder res/xml.
9. Paste-kan alamat IP Anda didalam tag <domain includeSubdomains="true">alamat ip</domain>
   contoh : 
10. Jalankan project dengan menekan tobol "Run App" atau dengan shortcut Shift + F10.
11. Selesai.
