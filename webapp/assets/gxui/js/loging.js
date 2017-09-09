function loading(canvas, options) {
    this.canvas = canvas;
    if (options) {
        this.radius = options.radius || 12;
        this.circleLineWidth = options.circleLineWidth || 12;
        this.circleColor = options.circleColor || '#1a2f4e';
        this.dotColor = options.dotColor || '#61a0ff';
    } else {
        this.radius = 12;
        this.circelLineWidth = 12;
        this.circleColor = '#1a2f4e';
        this.dotColor = '#61a0ff';
    }
}
loading.prototype = {
    show: function () {
        var canvas = this.canvas;
        if (!canvas.getContext) return;
        if (canvas.__loading) return;
        canvas.__loading = this;
        var ctx = canvas.getContext('2d');
        var radius = this.radius;
        //小球
        var rotators = [{ angle: 0, radius: 8 }, { angle: 0.2, radius: 8 }, { angle: 0.4, radius: 8 }, { angle: 0.6, radius: 8 }, { angle: 0.8, radius: 8 }, { angle: 1, radius: 8 }, { angle: 1.2, radius: 8 }, { angle: 1.4, radius: 8 }, { angle: 1.6, radius: 8 }, { angle: 1.8, radius: 8 }, { angle: 2, radius: 8 }, { angle: 2.2, radius: 8 }, { angle: 2.4, radius: 8 }, { angle: 2.6, radius: 8 }, { angle: 2.8, radius: 8 }, { angle: 3, radius: 8}];
        var me = this;
        canvas.loadingInterval = setInterval(function () {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            var lineWidth = me.circleLineWidth;
            var center = { x: canvas.width / 2, y: canvas.height / 2 };
            ctx.beginPath();
            ctx.lineWidth = lineWidth;
            ctx.strokeStyle = me.circleColor;
            ctx.arc(center.x, center.y, radius, 0, Math.PI * 2);
            ctx.closePath();
            ctx.stroke();
            for (var i = 0; i < rotators.length; i++) {
                var rotatorAngle = rotators[i].currentAngle || rotators[i].angle;
                //在圆圈上面画小圆   
                var rotatorCenter = { x: center.x - (radius) * Math.cos(rotatorAngle), y: center.y - (radius) * Math.sin(rotatorAngle) };
                var rotatorRadius = rotators[i].radius;
                ctx.beginPath();
                ctx.fillStyle = me.dotColor;
                ctx.arc(rotatorCenter.x, rotatorCenter.y, rotatorRadius, 0, Math.PI * 2);
                ctx.closePath();
                ctx.fill();
                rotators[i].currentAngle = rotatorAngle + 4 / radius; //速度
            }
        }, 20);
    },
    hide: function () {
        var canvas = this.canvas;
        canvas.__loading = false;
        if (canvas.loadingInterval) {
            window.clearInterval(canvas.loadingInterval);
        }
        var ctx = canvas.getContext('2d');
        if (ctx) ctx.clearRect(0, 0, canvas.width, canvas.height);
    }
};   