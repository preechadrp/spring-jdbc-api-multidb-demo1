- ทดสอบ spring boot jdbc api 
- ทดสอบเชื่อม 2 database
- @Bean Methods ใช้ใน @Configuration Classes

=== ลำดับการ override config Spring จะอ่านตามลำดับ === 
ลำดับการอ่านค่า config
 -D > Environment Varible > application.properties/.yml > (./config > ./ > src/main/resources)
 ตัวอย่าง
 1. application.properties เช่น abc.companyTaxId=mycomp 
 2. Environment Varible เช่น ABC_COMPANY_TAX_ID=mycomp
 3. -D เช่น java -Dabc.companyTaxId=mycomp -jar myapp.jar
