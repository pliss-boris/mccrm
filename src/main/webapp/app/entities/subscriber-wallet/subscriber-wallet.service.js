(function() {
    'use strict';
    angular
        .module('mccrmApp')
        .factory('SubscriberWallet', SubscriberWallet);

    SubscriberWallet.$inject = ['$resource', 'DateUtils'];

    function SubscriberWallet ($resource, DateUtils) {
        var resourceUrl =  'api/subscriber-wallets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.activationDate = DateUtils.convertDateTimeFromServer(data.activationDate);
                        data.created = DateUtils.convertDateTimeFromServer(data.created);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
