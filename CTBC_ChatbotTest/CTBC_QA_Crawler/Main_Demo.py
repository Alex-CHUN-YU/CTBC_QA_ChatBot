from CTBC_Crawler1 import Crawler as Crawler1
from CTBC_Crawler2 import Crawler as Crawler2

def main():
	crawler1 = Crawler1()
	crawler2 = Crawler2()
	crawler1.crawl()
	crawler2.crawl()

if __name__ == '__main__':
	main()
