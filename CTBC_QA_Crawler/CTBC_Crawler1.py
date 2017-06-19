import requests
from bs4 import BeautifulSoup
import json
import os

#中國信託信用卡QA爬蟲者
class Crawler:
	
	def __init__(self):
		self.session = requests.session()
		self.root = 'https://www.ctbcbank.com/CTCBPortalWeb/appmanager/ebank/rb?_nfpb=true&_pageLabel=TW_RB_CM_creditcard_000164'

	def crawl(self):
		qa = {}
		qa_list = []
		q = self.get_question()
		a = self.get_answer()
		for i in range(len(q)):
			qa['question'] = q[i]
			qa['answer'] = a[i]
			qa_list.append(qa.copy())
		self.ouput_board_page_articles_json(qa_list)
		#print(qa_list)

	def get_question(self):
		question = []
		try:
			#request url
			res = self.session.get(self.root)
			#successful request
			if res.status_code == 200:
				soup = BeautifulSoup(res.text, "html.parser")
				for q in soup.select(".question"):
					question.append(q.text.strip('\n').strip(' ').strip('\t').strip(' \n'))
			return question
		except Exception as e:
			return None

	def get_answer(self):
		answer = []
		try:
			#request url
			res = self.session.get(self.root)
			#successful request
			soup = BeautifulSoup(res.text, "html.parser")
			#print(soup)
			for a in soup.select(".answer_block"):
				#print('answer ' + a.text)
				answer.append(a.text.strip('\n').strip(' ').strip('\t').strip(' \n'))
			return answer
		except Exception as e:
			return None

	#輸出JSON格式
	def ouput_board_page_articles_json(self, res = None):
		if not os.path.exists("CTBC_Crawl_Result"):
			os.makedirs("CTBC_Crawl_Result")
		with open("CTBC_Crawl_Result/" + 'CTBC1' + ".json" , 'wb') as f:
			f.write(json.dumps(res, indent = 4, ensure_ascii = False).encode('utf-8'))