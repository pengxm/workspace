var WebMsg = React.createClass({
    /*
     *  三种状态
     *  onFocus    onBlur   onChange
     */
    
    getInitialState : function(){
        return {
            content : '',
            clickNum : 0
        }
    },
    handleFocus : function(){
        this.refs.news.style.display = 'none';
        this.refs.oPrompt.style.display = 'block';
    },
    handleBlur  : function(){
        this.refs.news.style.display = 'block';
        this.refs.oPrompt.style.display = 'none';
    },
    handleChange : function(e){
        this.setState({
            content : e.target.value
        })
    },
    handleClick : function(e){
        e.stopPropagation();
        e.preventDefault();
        this.state.clickNum ++;
        var oLi = document.createElement('li');
        oLi.innerHTML = this.state.clickNum + '. ' + this.state.content;
        this.refs.footcon.append(oLi);
    },
    render : function(){
        var textValue = this.state.content.length;
        var disabledS = (textValue > 0 && textValue <= 140) ? ' btn-light' : '';
        return (
            <div className="box">
            <div className="tit">
            <h3 ref="news">
            <a href="javascript:;">特朗普胜任美总统</a>
            </h3>
            <h2 className="txt" ref="oPrompt">
        <span>{textValue > 140 ? '已超出' : '还可以输入'}</span>
        <span>{textValue > 140 ? 140 - textValue : textValue - 140}</span>字
        </h2>
        </div>
        <div className="content">
            <textarea ref="oContent" name="con" className="cont" onFocus={this.handleFocus} onBlur={this.handleBlur} onChange={this.handleChange}></textarea>
        </div>
        <div className="footer">
            <a href="javascript:;" className={'btn' + disabledS} onClick={(textValue > 0 && textValue <= 140) ? this.handleClick : ''}>发布</a>
        </div>
        <ul ref="footcon"></ul>
            </div>
        )
        
    }
});




ReactDOM.render(
<WebMsg />,
    document.getElementById('container')
)