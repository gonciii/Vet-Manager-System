# Spring Boot Veteriner Yönetim Sistemi

## PROJE :
Bu proje, bir veteriner kliniğinin kendi işlerini yönetebildiği bir API uygulamasıdır.Uygulama veteriner çalışanı tarafından kullanılacaktır. Bu uygulama ile çalışanlar aşağıdaki işlemleri yapabilirler:
Veteriner doktorları kaydedebilir.
Doktorların çalışma günlerini kaydedebilir.
Müşterileri kaydedebilir.
Müşterilere ait hayvanları kaydedebilir.
Hayvanlara uygulanmış aşıları tarihleriyle birlikte kaydedebilir.
Hayvanlar için veteriner hekimlere randevu oluşturabilirler.

## Kullanılan Teknolojiler
Java
Spring Boot
PostgreSQL
MySQL
Spring Data JPA
Spring Web

IoC ve DI
Projede IoC (Inversion of Control) ve DI (Dependency Injection) için constructor injection kullanılmıştır.
Projede bulunan entity sınıfları ve aralarındaki ilişkiler belirlenmiştir.

## UML Diagrams
![Ekran Görüntüsü (65)](https://github.com/user-attachments/assets/ee987206-2315-4ef7-a580-276df2cc3c0f)

## Postman Collection
Tüm API endpointleri ilgili entity'ye göre Postman collection'a klasör klasör kaydedilmiştir. Bu collection export edilerek proje klasörüne eklenmiştir.

## API Endpoints

## Animal API Endpointleri

GET	/v1/animals	Tüm hayvanları listele
POST	/v1/animals	Yeni hayvan kaydet
GET	/v1/animals/{id}	Belirli bir hayvanı ID ile getir
PUT	/v1/animals/{id}	Belirli bir hayvanın bilgilerini güncelle
DELETE	/v1/animals/{id}	Belirli bir hayvanı sil
GET	/v1/animals/search?name={name}	İsme göre hayvanları filtrele
GET	/v1/customers/{id}/animals	Belirli bir müşteriye ait tüm hayvanları listele



