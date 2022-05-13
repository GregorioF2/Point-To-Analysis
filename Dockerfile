FROM ubuntu:bionic

RUN apt-get update; apt-get install curl -y; apt-get install wget -y
RUN curl -sL https://github.com/shyiko/jabba/raw/master/install.sh | bash && . ~/.jabba/jabba.sh

RUN wget https://souffle-lang.github.io/ppa/souffle-key.public -O /usr/share/keyrings/souffle-archive-keyring.gpg
RUN echo "deb [signed-by=/usr/share/keyrings/souffle-archive-keyring.gpg] https://souffle-lang.github.io/ppa/ubuntu/ stable main" | tee /etc/apt/sources.list.d/souffle.list
RUN apt-get update; apt-get install souffle


apt-get install r-base