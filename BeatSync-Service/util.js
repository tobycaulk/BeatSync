module.exports = {
    attachTimestamps: (obj) => {
        if(obj !== undefined) {
            if(obj.hasOwnProperty('details')) {
                obj.details.modifiedDate = Date.now();
            } else {
                obj.details = {};
                obj.details.createDate = Date.now();
                obj.details.modifiedDate = Date.now();
            }
        }
    },
    getRandomPin: () => {
        return Math.floor(1000 + (9999 - 1000) * Math.random());
    }
};