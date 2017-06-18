# CTBC ChatBot Q&A Test
CTBC ChatBot Q&A Test 主要以中國信託 問答集為聊天對象。ChatbotTest\TestFile.txt 中的問句為Train之資料集，是由中國信託Crawler所爬取下來的例句，由於題目有些敘述不清，故必須做些人工修正。</br>

## 使用方式
Input:</br>
```
1.執行WmmksIntentProducer.java檔
2.輸入句子(EX:如何申請預借現金密碼？)
```
Output:</br>
```
取出的Entity格式:
QW(如何) Act(辦理) Target(密碼) Feature([預借現金]) 
Your Question is:3.如何申請預借現金密碼？
Your Answer is:若您目前尚未設定或已遺忘預借現金密碼，請撥打0800-024-365按1輸入個人基本資料後輸入快撥鍵887由客服人員協助。
```
## 開發環境
Maven Project</br>
Eclipse Version: Neon.2 Release (4.6.2)</br>
JDK: java version "1.8.0_121"</br>
註: Eclipse 編碼如有問題請參照:</br>
File -> Properties > Resource >> Text file encoding >> UTF-8</br>
