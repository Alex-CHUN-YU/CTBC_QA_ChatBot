# CTBC_Crawler
CTBC_Crawler主要負責爬取中國信託銀行的Q&A問題，目前以以下兩個網址為爬取對象。

中國信託銀行常見Q&A(CTBC_Crawler1)</br>
https://www.ctbcbank.com/CTCBPortalWeb/appmanager/ebank/rb?_nfpb=true&_pageLabel=TW_RB_CM_creditcard_000164</br>

中國信託銀行常見Q&A(CTBC_Crawler2)</br>
https://www.ctbcbank.com/CTCBPortalWeb/appmanager/ebank/rb?_nfpb=true&_pageLabel=TW_RB_CM_service_000007&_windowLabel=T24004047331358757232751&_nffvid=%2FCTCBPortalWeb%2Fpages%2FcallCenter%2FcallCenterIntro.faces&firstView=true</br>

## 使用方式
Input:</br>
```
1. 執行Main_Demo.py檔
```
Output:</br>
* CTBC_Crawl_Result資料夾中CTBC1.json和CTBC2.json兩個JSON檔</br>


```
輸出格式範例:
    [
	    {
	    "question": "如何申請預借現金密碼？",
	    "answer": "若您目前尚未設定或已遺忘預借現金密碼，請撥打0800-024-365按1輸入個人基本資料後輸入快撥鍵887由客服人員協助。"
	    },
	    ...
    ]
```


## 開發環境
Python 3.5.2</br>
pip install requests</br>
pip install bs4</br>
pip install json</br>
pip install lxml</br>


