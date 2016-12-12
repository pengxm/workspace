require('normalize.css/normalize.css');
require('styles/App.css');

import React from 'react';
import ReactDOM from 'react-dom';

// 获取图片数据
let imageDatas = require('../data/imgDatas.json');
// 将图片名信息转为图片的路径
/*
 imageDatas = (function (imageDatasArr) {
 for(var i = 0, j = imageDatasArr.length; i < j; i++){
 var singleImageData = imageDatasArr[i];
 singleImageData.imageUrl = require('../images/'+ singleImageData.fileName);
 imageDatasArr[i] = singleImageData;
 }
 return imageDatasArr;
 })(imageDatas);
 */


imageDatas = ((imageDatasArr) => {
    for (var i = 0, j = imageDatasArr.length; i < j; i++) {
        let singleImageData = imageDatasArr[i];
        singleImageData.imageURL = require('../images/' + singleImageData.fileName);
        imageDatasArr[i] = singleImageData;
    }
    return imageDatasArr;
})(imageDatas);

/*
 * 获取区间内的随机值
 * */
function getRangeRandom(low, high){
    return Math.ceil(Math.random() * (high - low) + low);
}

class ImgFigure extends React.Component {
    constructor(props) {
        super(props);
    }
    render (){
        var styleObj = {};
        //如果props属性中指定了这张图片的位置,则使用
        if (this.props.arrange.pos) {
            styleObj = this.props.arrange.pos;
        }

        return (
            <figure className="img-figure" style={ styleObj }>
                <img src={this.props.data.imageURL} alt={this.props.data.title}/>
                <figcaption>
                    <h2 className="img-title">
                        {this.props.data.title}
                    </h2>
                </figcaption>
            </figure>
        );
    }
}



class AppComponent extends React.Component {
    constructor(props) {
        super(props);
        this.Constant = {
            centerPos: {
                left: 0,
                right: 0
            },
            hPosRange: { //水平方向的取值范围
                leftSecX: [0, 0],
                rightSecX: [0, 0],
                y: [0, 0]
            },
            vPosRange: { //垂直方向
                x: [0, 0],
                topY: [0, 0]
            }
        };

        this.state = {
            imagesArrangeArr: [
                // {
                //  pos:{
                //    left : '0',
                //    top : '0'
                //  }
                //    rotate:0, //旋转角度
                //isInverse:false //正反面
                //isCenter:false 图片是否居中
                // }
            ]
        };
    }

    /*
     *  重新布局图片位置
     *  centerIndex 指定居中图片index
     * */
    rearrange(centerIndex) {

        let imagesArrangeArr = this.state.imgsArrangeArr,
            Constant = this.Constant,
            centerPos = Constant.centerPos,
            hPosRange = Constant.hPosRange,
            vPosRange = Constant.vPosRange,
            hPosRangeLeftSecX = hPosRange.leftSecX,
            hPosRangeRightSecX = hPosRange.rightSecX,
            hPosRangeY = hPosRange.y,
            vPosRangeTopY = vPosRange.topY,
            vPosRangeX = vPosRange.x,
            imagesArrangTopArr = [],
            topImgNum = Math.floor(Math.random() * 2), //取一个或者不取
            topImgSpiceIndex = 0,
            imagesArrangeCenterArr = imagesArrangeArr.splice(centerIndex, 1);
        //首先居中centerIndex图片 ,centerIndex图片不需要旋转
        imagesArrangeCenterArr[0] = {
            pos: centerPos
        };
        //取出要布局上测的图片的状态信息
        topImgSpiceIndex = Math.floor(Math.random() * (imagesArrangeArr.length - topImgNum));
        imagesArrangTopArr = imagesArrangeArr.splice(topImgSpiceIndex, topImgNum);
        //布局位于上侧的图片
        imagesArrangTopArr.forEach((value, index) => {
            imagesArrangTopArr[index] = {
                pos: {
                    top: getRangeRandom(vPosRangeTopY[0], vPosRangeTopY[1]),
                    left: getRangeRandom(vPosRangeX[0], vPosRangeX[1])
                }

            };
        });

        //布局左两侧的图片
        for (let i = 0, j = imagesArrangeArr.length, k = j / 2; i < j; i++) {
            let hPosRangeLORX = null;

            //前半部分布局左边,右边部分布局右边
            if (i < k) {
                hPosRangeLORX = hPosRangeLeftSecX;
            } else {
                hPosRangeLORX = hPosRangeRightSecX
            }
            imagesArrangeArr[i] = {
                pos: {
                    top: getRangeRandom(hPosRangeY[0], hPosRangeY[1]),
                    left: getRangeRandom(hPosRangeLORX[0], hPosRangeLORX[1])
                }
            };
        }
        if (imagesArrangTopArr && imagesArrangTopArr[0]) {
            imagesArrangeArr.splice(topImgSpiceIndex, 0, imagesArrangTopArr[0]);
        }
        imagesArrangeArr.splice(centerIndex, 0, imagesArrangeCenterArr[0]);
        this.setState({
            imagesArrangeArr: imagesArrangeArr
        });


        /*
         *
         * var imagesArrangeArr = this.state.imagesArrangeArr,
         Constant = this.Constant,
         centerPos = Constant.centerPos,
         hPosRange = Constant.hPosRange,
         vPosRange = Constant.vPosRange,

         hPosRangeLeftSecX = hPosRange.leftSecX,
         hPosRangeRightSecX = hPosRange.rightSecX,
         hPosRangeY = hPosRange.y,

         vPosRangeTopY = vPosRange.topY,
         vPosRangeX = vPosRange.x,

         imagesArrangeTopArr = [],
         // 取一个或者不取
         topImgNum = Math.ceil(Math.random() * 2),
         topImgSpliceIndex = 0,
         imagesArrangeCenterArr = imagesArrangeArr.splice(centerIndex, 1);

         //首先居中 centerIndex 的图片
         imagesArrangeCenterArr[0].pos = centerPos;

         //取出上侧要布局图片的状态信息
         topImgSpliceIndex = Math.ceil(Math.random() * (imagesArrangeArr.length - topImgNum));
         imagesArrangeTopArr = imagesArrangeArr.splice(topImgSpliceIndex, topImgNum);

         // 布局位于上侧的图片
         imagesArrangeTopArr.forEach((value, index) => {
         imagesArrangeTopArr[index].pos = {
         top : getRangeRandom(vPosRangeTopY[0], vPosRangeTopY[1]),
         left : getRangeRandom(vPosRangeX[0], vPosRangeX[1])
         }
         });
         alert(2);
         //布局左右图片
         for(var i = 0, j = imagesArrangeArr.length, k = j/2; i < j; i++){
         var hPosRangeLORX = null;
         // 前半部分布局在左边， 右半部分布局右边
         if(i < k){
         hPosRangeLORX = hPosRangeLeftSecX;
         } else {
         hPosRangeLORX = hPosRangeRightSecX;
         }

         imagesArrangeArr[i].pos = {
         top : getRangeRandom(hPosRangeY[0], hPosRangeY[1]),
         left : getRangeRandom(hPosRangeLORX[0], hPosRangeLORX[1])
         }
         }
         //上侧图片填充
         if(imagesArrangeTopArr && imagesArrangeArr[0]){
         imagesArrangeArr.splice(topImgSpliceIndex, 0, imagesArrangeArr[0]);
         }
         //中心图片填充
         imagesArrangeArr.splice(centerIndex, 0, imagesArrangeCenterArr[0]);

         this.setState({
         imagesArrangeArr : imagesArrangeArr
         });
         *
         */


    }

    // 组件加载后为图片定位
    componentDidMount (){
        // 获取舞台的大小
        let stageDOM = ReactDOM.findDOMNode(this.refs.stage),
            stageW = stageDOM.scrollWidth,
            stageH = stageDOM.scrollHeight,
            halfStageW = Math.ceil(stageW / 2),
            halfStageH = Math.ceil(stageW / 2);
        // 获取图片的大小
        let imgFigureDOM = ReactDOM.findDOMNode(this.refs.imgFigure0),
            imgW = imgFigureDOM.scrollWidth,
            imgH = imgFigureDOM.scrollHeight,
            halfImgW = Math.ceil(imgW / 2),
            halfImgH = Math.ceil(imgH / 2);
        //计算中心图片的位置点
        this.Constant.centerPos = {
            left: halfStageW - halfImgW,
            top: halfStageH - halfImgH
        };
        //计算左侧,右侧区域图片排布的取值范围
        this.Constant.hPosRange.leftSecX[0] = -halfImgW;
        this.Constant.hPosRange.leftSecX[1] = halfStageW - halfImgW * 3;

        this.Constant.hPosRange.rightSecX[0] = halfStageW + halfImgW;
        this.Constant.hPosRange.rightSecX[1] = stageW - halfImgW;

        this.Constant.hPosRange.y[0] = -halfImgH;
        this.Constant.hPosRange.y[1] = stageH - halfImgH;
        //计算上测区域图片排布的取值范围
        this.Constant.vPosRange.topY[0] = -halfImgH;
        this.Constant.vPosRange.topY[1] = halfStageH - halfImgH * 3;

        this.Constant.vPosRange.x[0] = halfStageW - imgW;
        this.Constant.vPosRange.x[1] = halfStageW;

        this.rearrange(0);
    }




    render() {

        var controllerUnits = [],
            imgFigures = [];
        imageDatas.forEach(function(value, index){
            alert(value +' : '+ index)
        });
        imageDatas.forEach((value, index) =>{
            if(!this.state.imagesArrangeArr.index){
                this.state.imagesArrangeArr[index] = {
                    pos : {
                        left: 0,
                        top : 0
                    }
                }
            }

            imgFigures.push(<ImgFigure data={value} key={index} ref={'imgFigure' + index} arrange={this.state.imagesArrangeArr[index]}/>)
        });
        return (
            <section className="stage" ref="stage" >
                <section className="image-sec">
                    {imgFigures}
                </section>
                <nav className="controller-nav">
                    {controllerUnits}
                </nav>
            </section>
        );
    }
}

AppComponent.defaultProps = {
};

export default AppComponent;
