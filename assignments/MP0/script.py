import sys
# for line in sys.stdin:
#     print line
#
# print "Arg - ", sys.argv[1]
import operator

stopWordsList = ["i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"]

# str =' Manas '
# print str, len(str)
# print str.strip(), len(str.strip())
#
# print "meeeen" in stopWordsList
# print str.lower()

#
# import operator
# x = {1: 2, 3: 4, 4: 3, 2: 1, 0: 0}
# sorted_x = sorted(x.items(), key=operator.itemgetter(1), reverse=True)
# sorted_x = sorted(x.items(), key=operator.itemgetter(0), reverse=True)
# print type(sorted_x), sorted_x
# print sorted_x[:2]

a='Beautiful, is; be\ttt;er*than\nugly.hello*world{a}'
import re
# print re.split('; |, |\*|\n',a)

delimiters = " \t,;.?!-:@[](){}_*/"
# print a.split(r'\t,;.?!-:@[](){}_*/',a)

# print re.split(r'[\t,;.?!-:@[](){}_*/]+', a)
# print re.split(r'[ \t,;.?!-:@[\](){}_*/]', 'Think Th.is,is;a,;str?in\tghello!world-ye:swhy@no[tabc]ddd(math_xx)yy{gg}manas_mukherjee***best')
# print re.split(r'[ \t,;.?!-:@[\\](){}_*/]+', 'The\t\tman,,has;an.amazing?look!but-the:girl@didnt[like\her]This(is)so{strange}thing_that*noone/can')
limter = r'[ \t,;.?!\-:@[\\\](){}_*/+]'
print re.split(limter, 'CC&A The\t\tman,,has;an.amazing?look!but-the:girl@didnt[like\her]This(is)so{strange}thing_that*noone/can')
print re.split(r'[\t,;?!-:@[](){}_*]+', 'This,is;a,;str?in\tg')
#

# 30th_Waffen_Grenadier_Division_of_the_SS_(1st_Belarussian)
# 1st_Bangor_Old_Boys_F.C.
# Henry_Conyngham,_1st_Marquess_Conyngham

# d = {'k1':1, 'k2':25, 'k3':3, 'k4':4, 'j4':4, 'k5':5, 'c2':25}
# l = [(key, value) for key, value in d.items()]
# print(l)
#
#
# # sorted_x = sorted(l, key=lambda tup: (-tup[0], tup[1]))
# # list1 = sorted(l, key=operator.itemgetter(1,0))
# sorted_x = sorted(l, key=lambda tup: (-tup[1], tup[0]))
# print(sorted_x)

x = "Slaves_&_Masters"
print re.split(limter,x)


