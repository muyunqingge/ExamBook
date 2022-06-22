package com.example.exambooktest.utils;
    public class Msg{
        private String notice;
        private String name;
        private String msg;
        private String time;


        public Msg(String notice, String name, String msg, String time)
        {
            this.notice = notice;
            this.name = name;
            this.msg = msg;
            this.time = time;
        }

        public String getNotice()
        {
            return notice;
        }

        public void setNotice(String notice)
        {
            this.notice = notice;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getMsg()
        {
            return msg;
        }

        public void  setMsg(String msg)
        {
            this.msg = msg;
        }

        public String getTime()
        {
            return time;
        }

        public void  setTime(String time)
        {
            this.time = time;
        }
    }

