# CTBC ChatBot Q&A Test
![demo]()
CTBC ChatBot Q&A Test 主要以中國信託問答集為聊天對象。CTBC_ChatbotTest\TestFile.txt 中的問句為Train之資料集。問答資料集是由中國信託Crawler(CTBC_ChatbotTest\CTBC_QA_Crawler)所爬取下來的例句，由於題目有些敘述不清，故必須做些人工修正。</br>

## 使用方式
Input:</br>
```
1.下載 https://drive.google.com/file/d/0B4rlWa2S_JMBckFvNlVzc3BpWVk/view?usp=sharing
2.將 typesafe-activator-1.3.12 資料夾的 bin 目錄放到環境變數的 PATH 中
3.開啟cmd, 進入CTBC_ChatbotTest 執行activator()
4.輸入run(test)
5.開啟UI資料夾內的index2.html
6.輸入句子進行測試(EX:如何申請預借現金密碼？)
註:CTBC_ChatbotTest\TestFile.txt如有新增句子，可透過TermWeightProduce.java來產生新的權重表
```
Output:</br>
```
"若您目前尚未設定或已遺忘預借現金密碼，請撥打0800-024-365按1輸入個人基本資料後輸入快撥鍵887由客服人員協助。"
```

## 開發環境
Play Framework Project</br>
IDEA Version:IntelliJ IDEA 2017.1.2 x64</br>
JDK: java version "1.8.0_121"</br>
Scala Version: 2017.1.19
