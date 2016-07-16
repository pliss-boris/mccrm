(function() {
    'use strict';
    angular
        .module('mccrmApp')
        .factory('CustomerAddres', CustomerAddres);

    CustomerAddres.$inject = ['$resource'];

    function CustomerAddres ($resource) {
        var resourceUrl =  'api/customer-addres/:id';

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
