library("arules");
library("arulesViz");

script.dir <- dirname(sys.frame(1)$ofile);
setwd(script.dir);
txn = read.transactions(file="nodefailure_cleaned.txt", format="basket",sep=",");

rules=apriori(txn, parameter = list(target="rules",support=0.7, confidence=0.7, minlen=2, maxlen=4));
inspect(rules);
plot(rules);
