import "./Table.css";

function Table({ children }) {

    return (

        <div className="table-container">

            <table className="erp-table">

                {children}

            </table>

        </div>

    );

}

export default Table;