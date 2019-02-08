
'''
Output didn't match
'''

import random
import os
import string
import sys
import re

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

delimiters = " \t,;.?!-:@[](){}_*/"

def getIndexes(seed):
    random.seed(seed)
    n = 10000
    number_of_lines = 50000
    ret = []
    for i in range(0,n):
        ret.append(random.randint(0, 50000-1))
    return ret

def process(userID):
    indexes = getIndexes(userID)
    ret = []
    # TODO

    input_lines = []
    for ind, line in enumerate(sys.stdin):
        input_lines.append(line)

    input_filtered_lines = []
    for i in indexes:
        input_filtered_lines.append(input_lines[i])

    word_freq_dict = {}
    for line_id, line in enumerate(input_filtered_lines):

        splitted_lines = re.split(r'[ \t,;.?!\-:@[\\\](){}_*/]',line)
        for word_ind, word in enumerate(splitted_lines):
            word_lw_stripped = word.strip().lower()

            if word_lw_stripped not in stopWordsList and word_lw_stripped:
                if word_lw_stripped in word_freq_dict:
                    word_freq_dict[word_lw_stripped] +=1
                else:
                    word_freq_dict[word_lw_stripped] =1

    word_freq_dict_as_list = [(key, value) for key, value in word_freq_dict.items()]
    sorted_x = sorted(word_freq_dict_as_list, key=lambda tup: (-tup[1], tup[0]))

    for ind, (word, freq) in enumerate(sorted_x[:20]):
        print (word, freq)

process(sys.argv[1])

