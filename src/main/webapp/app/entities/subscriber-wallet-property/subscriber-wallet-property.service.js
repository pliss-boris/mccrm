(function() {
    'use strict';
    angular
        .module('mccrmApp')
        .factory('SubscriberWalletProperty', SubscriberWalletProperty);

    SubscriberWalletProperty.$inject = ['$resource', 'DateUtils'];

    function SubscriberWalletProperty ($resource, DateUtils) {
        var resourceUrl =  'api/subscriber-wallet-properties/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.created = DateUtils.convertDateTimeFromServer(data.created);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
