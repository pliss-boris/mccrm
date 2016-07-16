(function() {
    'use strict';
    angular
        .module('mccrmApp')
        .factory('CustomerContact', CustomerContact);

    CustomerContact.$inject = ['$resource'];

    function CustomerContact ($resource) {
        var resourceUrl =  'api/customer-contacts/:id';

        return $resource(resourceUrl, {}, {
            'queryByCustomerId': {
                method: 'GET',
                isArray: true,
                url: 'api/customer-contacts/customer/:id'
            },
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
