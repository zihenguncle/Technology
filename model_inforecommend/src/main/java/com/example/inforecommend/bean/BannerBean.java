package com.example.inforecommend.bean;

import java.util.List;

public class BannerBean {

    /**
     * result : [{"imageUrl":"http://172.17.8.100/images/tech/banner/073040514318.jpg","jumpUrl":"wd://information?infoId=1","rank":1,"title":"关于滴滴顺风车事件的几点思考"},{"imageUrl":"http://172.17.8.100/images/tech/banner/205315638398.jpg","jumpUrl":"https://www.huxiu.com/article/266392.html","rank":2,"title":"38个区县大PK：重庆也要东南飞？"},{"imageUrl":"http://172.17.8.100/images/tech/banner/201540980386.jpg","jumpUrl":"https://www.huxiu.com/article/266362.html","rank":3,"title":"千股大跌，他财富一天缩水630亿元，你感受过这种绝望嘛"},{"imageUrl":"http://172.17.8.100/images/tech/banner/145637698331.jpg","jumpUrl":"https://www.huxiu.com/article/266201.html","rank":4,"title":"华为寻找AI"},{"imageUrl":"http://172.17.8.100/images/tech/banner/065326098728.jpg","jumpUrl":"https://www.huxiu.com/article/266398.html","rank":5,"title":"微信\u201c死于\u201d印度"},{"imageUrl":"http://172.17.8.100/images/tech/banner/20181026151647.png","jumpUrl":"http://172.17.8.100/htm/lottery/index.html","rank":5,"title":"抽奖"}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * imageUrl : http://172.17.8.100/images/tech/banner/073040514318.jpg
         * jumpUrl : wd://information?infoId=1
         * rank : 1
         * title : 关于滴滴顺风车事件的几点思考
         */

        private String imageUrl;
        private String jumpUrl;
        private int rank;
        private String title;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
