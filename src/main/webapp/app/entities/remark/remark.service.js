(function() {
    'use strict';
    angular
        .module('mccrmApp')
        .factory('Remark', Remark);

    Remark.$inject = ['$resource'];

    function Remark ($resource) {
        var resourceUrl =  'api/remarks/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
