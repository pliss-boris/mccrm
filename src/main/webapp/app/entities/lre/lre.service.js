(function() {
    'use strict';
    angular
        .module('mccrmApp')
        .factory('Lre', Lre);

    Lre.$inject = ['$resource'];

    function Lre ($resource) {
        var resourceUrl =  'api/lres/:id';

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
